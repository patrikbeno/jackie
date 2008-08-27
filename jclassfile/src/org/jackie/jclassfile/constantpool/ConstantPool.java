package org.jackie.jclassfile.constantpool;

import org.jackie.jclassfile.constantpool.impl.Factory;
import org.jackie.jclassfile.model.Base;
import org.jackie.jclassfile.model.ClassFile;
import static org.jackie.jclassfile.ClassFileContext.classFileContext;
import org.jackie.utils.Log;

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
public class ConstantPool extends Base {

	static public ConstantPool constantPool() {
		return classFileContext().constantPool();
	}

	ClassFile classfile;

	List<Constant> constants;

	Factory factory = new Factory(this){};
	Map<Constant,Constant> registry = new HashMap<Constant, Constant>();

	public ConstantPool(ClassFile classfile) {
		this.classfile = classfile;
		constants = new ArrayList<Constant>();
	}

	public ConstantPool(ClassFile classfile, DataInput in) throws IOException {
		this.classfile = classfile;
		load(in);
	}

	public Factory factory() {
		return factory;
	}

	public void load(DataInput in) throws IOException {
		int count = in.readUnsignedShort()-1;
		constants = new ArrayList<Constant>(count);

		Log.debug("Loading constant pool (%s entries)", count);

		// round #1: register
		while (count > 0) {
			count -= register(createConstant(in));
		}

		// round #2: resolve/initialize
		for (Constant c : constants) {
			if (c != null && !c.isResolved()) {
				c.resolve();
			}
		}
	}

	public void save(DataOutput out) throws IOException {
		Log.debug("Saving constant pool (%s entries)", constants.size());

		out.writeShort(constants.size()+1);
		for (Constant c : constants) {
			if (c == null) { continue; } // skip long value placeholders (Constant.isLongValue())
			c.save(out);
		}
	}

	protected int register(Constant constant) {
		constants.add(constant);
		constant.setIndex(constants.size());
		if (constant.isLongData()) { // long data (long, double) occupy two entries in the pool
			constants.add(null);
		}
		registry.put(constant, constant);

		return constant.isLongData() ? 2 : 1;
	}

	public <T extends Constant> T getConstant(int index, Class<T> type) {
		if (index == 0) { return null; }

		Constant c = constants.get(index-1);
		return type.cast(c);
	}

	protected Constant createConstant(DataInput in) throws IOException {
		CPEntryType type = CPEntryType.forCode(in.readUnsignedByte());
		Constant c = factory.createConstant(type);
		c.setResolver(c.readConstantDataOrGetResolver(in));
		return c;
	}

	public <T extends Constant> T intern(T constant) {
		T c = (T) registry.get(constant);
		if (c != null) { return c; }

		register(constant);
		return constant;
	}

}
