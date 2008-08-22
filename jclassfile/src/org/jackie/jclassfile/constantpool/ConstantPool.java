package org.jackie.jclassfile.constantpool;

import org.jackie.jclassfile.constantpool.impl.Factory;
import org.jackie.jclassfile.model.Base;
import org.jackie.jclassfile.model.ClassFile;

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

	ClassFile classfile;

	List<Constant> constants;

	Factory factory = new Factory(this){};
	Map<Constant,Constant> registry = new HashMap<Constant, Constant>();


	public ConstantPool(ClassFile classfile, DataInput in) throws IOException {
		this.classfile = classfile;
		load(in);
	}

	void register(Constant constant) {
		if (constant.getIndex() != null) { return; }

		constants.add(constant);
		constant.setIndex(constants.size());
		if (constant.isLongData()) {
			constants.add(null);
		}
	}

	public <T extends Constant> T getConstant(int index, Class<T> type) {
		Constant c = constants.get(index-1);
		if (!c.isResolved()) {
			c.resolve();
		}
		return type.cast(c);
	}

	public void load(DataInput in) throws IOException {
		int count = in.readUnsignedShort()-1;
		constants = new ArrayList<Constant>(count);
		while (count-- > 0) {
			Constant c = createConstant(in);
			register(c);
			if (c.isLongData()) { count--; }
		}

		// should not be needed, just make loaded stuff consistent in memory
		// when the code is debugged, lazy loading should be restored
		boolean lazyLoading = false;
		if (!lazyLoading) {
			for (Constant c : constants) {
				if (c != null && !c.isResolved()) {
					c.resolve();
				}
			}
		}
	}

	public void save(DataOutput out) throws IOException {
		out.writeShort(constants.size()+1);
		for (Constant constant : constants) {
			if (constant==null) { continue; }

			if (!constant.isResolved()) {
				constant.resolve();
			}
			constant.save(out);
		}
	}

	protected Constant createConstant(DataInput in) throws IOException {
		CPEntryType type = CPEntryType.forCode(in.readUnsignedByte());
		Constant c = factory.createConstant(type);
		c.setResolver(c.readConstantDataOrGetResolver(in));
		return c;
	}

	public <T extends Constant> T resolve(T constant) {
		T c = (T) registry.get(constant);
		if (c != null) { return c; }

		registry.put(constant, constant);
		return constant;
	}



}
