package org.jackie.jclassfile.constantpool;

import org.jackie.jclassfile.model.Base;
import org.jackie.jclassfile.code.ConstantPoolSupport;
import org.jackie.utils.Log;
import org.jackie.utils.Assert;
import org.jackie.utils.Closeable;
import org.jackie.utils.XDataInput;
import org.jackie.utils.WriteOnlyList;
import org.jackie.utils.XDataOutput;
import static org.jackie.utils.Assert.typecast;
import static org.jackie.utils.Assert.NOTNULL;
import static org.jackie.utils.Assert.expected;
import static org.jackie.utils.Assert.doAssert;
import org.jackie.context.ContextObject;
import static org.jackie.context.ContextManager.context;
import static org.jackie.context.ContextManager.contextManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Formattable;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Set;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

/**
 * @author Patrik Beno
 */
public class ConstantPool extends Base implements ContextObject, Closeable, Iterable<Constant> {

	static public ConstantPool constantPool() {
		ConstantPool pool = context().get(ConstantPool.class);
		return NOTNULL(pool);
	}

	static public boolean available() {
		return contextManager().hasContext() && context().get(ConstantPool.class) != null;
	}

	static public ConstantPool create(boolean bindToContext) {
		ConstantPool pool = new ConstantPool();
		if (bindToContext) {
			context().set(ConstantPool.class, pool);
		}
		return pool;
	}


	// normalized and ordered (by index) constants as written in physical constant pool
	// result of load() or rebuild()
	List<Constant> constants;

	// registry for constants that must be in lower pool (index <= 255)
	Set<Constant> lowindex;

	// constant index, provided for fast index lookup
	Map<Constant,Integer> index;

	// instance internization: for fast constant by value lookup
	Map<Constant,Constant> identity;

	{
		init();
	}

	void init() {
		constants = new ArrayList<Constant>();
		lowindex = new HashSet<Constant>();
		index = new HashMap<Constant, Integer>();
		identity = new HashMap<Constant, Constant>();
	}

	public void load(XDataInput in, ConstantPool pool) {
		init();

		int count = in.readUnsignedShort();

		Log.debug("Loading constant pool (size: %s)", count);

		List<Task> secondPassResolvers = new WriteOnlyList<Task>();

		// round #1: register constants
		constants.add(null); // #0 is reserved by JVM for NULL and it's not saved in pool
		for (int i = 1; i < count; i++) {
			expected(constants.size(), i, "broken constant index?");

			Constant c = loadConstant(in, secondPassResolvers);
			constants.add(c);

			if (c.isLongData()) {
				i++;
				constants.add(null);
			}
		}

		// round #2: resolve/initialize
		for (Task resolver : secondPassResolvers) {
			resolver.execute();
		}

		// round #3: build index
		reindex();

		Log.debug("Loaded constant pool (%s non-null entries, total pool size: %s)", 
					 countNotNull(constants), constants.size());
		for (Constant c : constants) {
			if (c == null) { continue; }
			Log.debug("\t%s", c);
		}

	}

	public void rebuild() {

		List<Constant> all = new ArrayList<Constant>();
		for (Constant c : constants) {
			if (c != null) { all.add(c); }
		}
		
		Collections.sort(all, new Comparator<Constant>() {
			public int compare(Constant c1, Constant c2) {
				// constants in lowindex go first, no matter what
				if (lowindex.contains(c1)) { return -1; }
				if (lowindex.contains(c2)) { return 1; }
				// if none of them is in lowindex, compare by their type code
				return Integer.valueOf(c1.type().code()).compareTo(c2.type().code());
			}
		});

		List<Constant> newlist = new ArrayList<Constant>();
		newlist.add(null); // #0 is reserved by JVM for NULL and it's not saved in pool
		for (Constant c : all) {
			newlist.add(c);
			if (c.isLongData()) { newlist.add(null); }
		}

		this.constants = new ArrayList<Constant>(newlist);
		reindex();
	}

	void reindex() {
		Map<Constant,Integer> newindex = new HashMap<Constant, Integer>();
		int idx = 0;
		for (Constant c : constants) {
			if (c != null) { newindex.put(c, idx); }
			idx++;
		}

		this.index = newindex;
	}

	public void save(XDataOutput out) {
		rebuild();

		Log.debug("Saving constant pool (%s non-null entries, total pool size: %s)",
					 countNotNull(constants), constants.size());

		out.writeShort(constants.size());
		for (Constant c : constants) {
			if (c == null) { continue; }
			Log.debug("\t%s", c);
			c.save(out);
		}
	}

	public <T extends Constant> T getConstant(int index, Class<T> type) {
		if (index == 0) { return null; }

		doAssert(index <= constants.size(),
					"Invalid constant pool index %s, requested type %s (Pool size: %s)",
					index, type.getSimpleName(), constants.size());


		T c = typecast(constants.get(index), type);
		return NOTNULL(c, "No constant found for index %s", index);
	}

	public int indexOf(Constant constant) {
		NOTNULL(index, "ConstantPool not indexed.");

		Integer idx = index.get(constant);
		NOTNULL(idx, "Unregistered or foreign constant: %s", constant);

		return idx;
	}

	protected Constant loadConstant(XDataInput in, List<Task> secondPassResolvers) {
		CPEntryType type = CPEntryType.forCode(in.readUnsignedByte());
		Constant c = type.loader().load(in, this, secondPassResolvers);
		c.pool = this;
		return c;
	}

	public void close() {
		for (Constant c : this) {
			if (c != null) { c.detach(); }
		}
		constants.clear();
		lowindex.clear();
		index.clear();
		identity.clear();
	}

	public boolean isRegistered(Constant constant) {
		return identity.containsKey(constant);
	}

	public <T extends Constant> T register(T constant) {
		T registered = (T) identity.get(constant);
		if (registered != null) {
			return registered;
		}

		// delegate to register dependent constants
		if (constant instanceof ConstantPoolSupport) {
			((ConstantPoolSupport) constant).registerConstants(this);
		}

		// finally register this constant
		constant.pool = this;
		identity.put(constant, constant);
		constants.add(constant);

		return constant;
	}

	public <T extends Constant> T register(T constant, boolean asLowIndex) {
		if (asLowIndex && !lowindex.contains(constant)) {
			doAssert(lowindex.size() < 255, "Cannot register 'low index' constant. LowIndex buffer is full. Constant: %s", constant);
		}

		T registered = register(constant);
		lowindex.add(registered);

		return registered;
	}

	Formattable countNotNull(final List<?> list) {
		return new Formattable() {
			public void formatTo(Formatter formatter, int flags, int width, int precision) {
				int count = 0;
				for (Object o : list) {
					if (o != null) { count++; }
				}
				formatter.format("%s", count);
			}
		};
	}

	public Iterator<Constant> iterator() {
		return new Iterator<Constant>() {

			int index;

			public boolean hasNext() {
				return index < constants.size();
			}

			public Constant next() {
				return constants.get(index++);
			}

			public void remove() {
				throw Assert.unsupported(); 
			}
		};
	}
}
