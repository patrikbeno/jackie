package org.jackie.jclassfile.constantpool.impl;

import org.jackie.jclassfile.constantpool.CPEntryType;
import org.jackie.jclassfile.constantpool.Constant;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.code.ConstantPoolSupport;
import org.jackie.utils.XDataInput;
import org.jackie.utils.XDataOutput;

/**
 * @author Patrik Beno
 */
public class ClassRef extends Constant implements ValueProvider, ConstantPoolSupport {

	/*
CONSTANT_Class_info {
    	u1 tag;
    	u2 name_index;
    }
	 */

	static public final Loader LOADER = new Loader() {
		protected Constant create() {
			return new ClassRef(); 
		}
	};

	static public ClassRef create(Utf8 value) {
		return new ClassRef(value);
	}


	Utf8 value;

	protected ClassRef() {
	}

	protected ClassRef(Utf8 value) {
		this.value = value;
	}

	public CPEntryType type() {
		return CPEntryType.CLASS;
	}

	public void registerConstants(ConstantPool pool) {
		value = pool.register(value);
	}

	public String value() {
		return value.value();
	}

	protected Task readConstantDataOrGetResolver(XDataInput in, final ConstantPool pool) {
		final int nameidx = in.readUnsignedShort();
		return new Task() {
			public void execute() {
				value = pool.getConstant(nameidx, Utf8.class);
			}
		};
	}

	protected void writeConstantData(XDataOutput out) {
		value.writeReference(out);
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ClassRef classRef = (ClassRef) o;

		if (value != null ? !value.equals(classRef.value) : classRef.value != null) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		return (value != null ? value.hashCode() : 0);
	}

	protected String valueToString() {
		return value != null ? value.value() : null;
	}
}
