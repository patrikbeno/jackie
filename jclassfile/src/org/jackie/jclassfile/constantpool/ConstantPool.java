package org.jackie.jclassfile.constantpool;

import org.jackie.jclassfile.constantpool.impl.Factory;
import org.jackie.jclassfile.model.Base;
import org.jackie.jclassfile.model.ClassFile;
import org.jackie.utils.Log;
import org.jackie.utils.Assert;
import org.jackie.utils.Closeable;
import static org.jackie.utils.Assert.typecast;
import static org.jackie.utils.Assert.NOTNULL;
import static org.jackie.utils.Assert.expected;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Patrik Beno
 */
public class ConstantPool extends Base implements Closeable {

	ClassFile classfile;

	List<Constant> constants;
	Map<Constant,Integer> indices;

	Factory factory;

	{
		factory = new Factory(this);
	}

	public ConstantPool(ClassFile classfile) {
		this.classfile = classfile;
		init();
	}

	void init() {
		constants = new ArrayList<Constant>();
		constants.add(null); // index #0 is reserved
		indices = new HashMap<Constant, Integer>();
	}

	public Factory factory() {
		return factory;
	}

	public void load(DataInput in) throws IOException {
		init();

		indices = null; // will rebuild index after all constants are read

		int count = in.readUnsignedShort() - 1;

		Log.debug("Loading constant pool (%s entries)", count);

		List<Task> resolvers = new ArrayList<Task>(count);

		// round #1: register constants
		for (int i = 1; i <= count; i++) {
			expected(constants.size(), i, "constant index?");

			Constant constant = createConstant(in);
			constants.add(constant);

			Task resolver = constant.readConstantDataOrGetResolver(in);
			if (resolver != null) { resolvers.add(resolver); }

			if (constant.isLongData()) {
				i++;
				constants.add(null);
			}
		}

		// round #2: resolve/initialize
		for (Task resolver : resolvers) {
			resolver.execute();
		}

		// round #3: build index
		buildIndex: {
			indices = new HashMap<Constant, Integer>(constants.size());
			int i = 0;
			for (Constant c : constants) {
				if (c != null) {
					indices.put(c, i);
				}
				i++;
			}
		}
	}

	public void save(DataOutput out) throws IOException {
		Log.debug("Saving constant pool (%s entries)", constants.size());

		out.writeShort(constants.size());
		for (Constant c : new ArrayList<Constant>(constants)) {
			if (c == null) { continue; } // skip #0 (null) and long value placeholders (Constant.isLongValue())
			c.save(out);
		}
	}

	public <T extends Constant> T getConstant(int index, Class<T> type) {
		if (index == 0) { return null; }

		T c = typecast(constants.get(index), type);
		return NOTNULL(c, "No constant found for index %s", index);
	}

	protected Constant createConstant(DataInput in) throws IOException {
		CPEntryType type = CPEntryType.forCode(in.readUnsignedByte());
		Constant c = factory.createConstant(type);
		return c;
	}

	public <T extends Constant> T intern(T constant) {
		Integer idx = indexOf(constant, true);
		Constant interned = constants.get(idx);
		return Assert.typecast(interned, (Class<T>) constant.getClass());
	}

	public void close() {
	}

	public Integer indexOf(Constant constant) {
		return indexOf(constant, false);
	}

	public Integer indexOf(Constant constant, boolean register) {
		Integer idx = indices.get(constant);
		if (idx == null && register) {
			constants.add(constant);
			idx = constants.size() - 1;
			indices.put(constant, idx);
			if (constant.isLongData()) { // long data (long, double) occupy two entries in the pool
				constants.add(null);
			}
		}
		return idx;
	}

}
