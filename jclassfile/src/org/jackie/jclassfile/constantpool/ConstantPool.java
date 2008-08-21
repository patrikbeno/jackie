package org.jackie.jclassfile.constantpool;

import org.jackie.utils.Assert;
import org.jackie.utils.Log;
import org.jackie.jclassfile.model.Base;
import org.jackie.jclassfile.model.ClassFile;
import org.jackie.jclassfile.constantpool.impl.ClassRef;
import org.jackie.jclassfile.constantpool.impl.FieldRef;
import org.jackie.jclassfile.constantpool.impl.MethodRef;
import org.jackie.jclassfile.constantpool.impl.InterfaceMethodRef;
import org.jackie.jclassfile.constantpool.impl.StringRef;
import org.jackie.jclassfile.constantpool.impl.IntegerRef;
import org.jackie.jclassfile.constantpool.impl.FloatRef;
import org.jackie.jclassfile.constantpool.impl.LongRef;
import org.jackie.jclassfile.constantpool.impl.DoubleRef;
import org.jackie.jclassfile.constantpool.impl.NameAndType;
import org.jackie.jclassfile.constantpool.impl.Utf8;
import org.jackie.jclassfile.constantpool.impl.Factory;

import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;
import java.lang.ref.WeakReference;

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
		constants.add(constant);
		constant.setIndex(constants.size());
		if (constant.isLongData()) {
			constants.add(null);
		}
		Log.debug("%s", constant);
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
		Log.debug("Reading ConstantPool (%s entries)", count);
		constants = new ArrayList<Constant>(count);
		while (count-- > 0) {
			Constant c = createConstant(in);
			register(c);
			if (c.isLongData()) { count--; }
		}
	}

	public void save(DataOutput out) throws IOException {
		out.writeInt(constants.size());
		for (Constant constant : constants) {
			if (constant==null) { continue; }
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
