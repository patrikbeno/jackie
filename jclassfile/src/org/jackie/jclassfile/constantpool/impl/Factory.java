package org.jackie.jclassfile.constantpool.impl;

import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.constantpool.Constant;
import org.jackie.jclassfile.constantpool.CPEntryType;
import org.jackie.utils.Assert;

/**
 * @author Patrik Beno
 */
public abstract class Factory {

	ConstantPool pool;

	protected Factory(ConstantPool pool) {
		this.pool = pool;
	}

	public Constant createConstant(CPEntryType type) {
		switch (type) {
			case UTF8:
				return new Utf8(pool);
			case CLASS:
				return new ClassRef(pool);
			case FIELDREF:
				return new FieldRef(pool);
			case METHODREF:
				return new MethodRef(pool);
			case INTERFACE_METHODREF:
				return new InterfaceMethodRef(pool);
			case NAME_AND_TYPE:
				return new NameAndType(pool);
			case STRING:
				return new StringRef(pool);
			case INTEGER:
				return new IntegerRef(pool);
			case FLOAT:
				return new FloatRef(pool);
			case LONG:
				return new LongRef(pool);
			case DOUBLE:
				return new DoubleRef(pool);
			default:
				throw Assert.invariantFailed(type);
		}
	}

	private <T extends Constant> T resolve(T constant) {
		return pool.resolve(constant);
	}

	public Utf8 getUtf8(String value) {
		return resolve(new Utf8(pool, value));
	}

	public ClassRef getClassRef(String classname) {
		return resolve(new ClassRef(pool, classname));
	}

	public FieldRef getFieldRef(String clsname, String name, String type) {
		return resolve(new FieldRef(pool, clsname, name, type));
	}

	public NameAndType getNameAndType(String name, String type) {
		return resolve(new NameAndType(pool, name, type));
	}

	public StringRef getStringRef(String value) {
		return resolve(new StringRef(pool, value));
	}



}
