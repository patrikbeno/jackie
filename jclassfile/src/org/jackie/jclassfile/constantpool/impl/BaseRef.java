package org.jackie.jclassfile.constantpool.impl;

import org.jackie.jclassfile.constantpool.Constant;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.utils.XDataInput;
import org.jackie.utils.XDataOutput;

/**
 * @author Patrik Beno
 */
public abstract class BaseRef extends Constant {

	/*
CONSTANT_Fieldref_info {
    	u1 tag;
    	u2 class_index;
    	u2 name_and_type_index;
    }
	 */

	ClassRef classref;
	NameAndType nametype;

	protected BaseRef() {
	}

	public void register() {
		classref.register();
		nametype.register();
		super.register();
	}

	protected BaseRef(ClassRef classref, NameAndType nametype) {
		this.classref = classref;
		this.nametype = nametype;
	}

	public ClassRef classref() {
		return classref;
	}

	public NameAndType nametype() {
		return nametype;
	}

	protected Task readConstantDataOrGetResolver(XDataInput in, final ConstantPool pool) {
		final int classidx = in.readUnsignedShort();
		final int nametypeidx = in.readUnsignedShort();
		return new Task() {
			public void execute() {
				classref = pool.getConstant(classidx, ClassRef.class);
				nametype = pool.getConstant(nametypeidx, NameAndType.class);
			}
		};
	}

	protected void writeConstantData(XDataOutput out) {
		classref.writeReference(out);
		nametype.writeReference(out);
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BaseRef baseRef = (BaseRef) o;

		if (classref != null ? !classref.equals(baseRef.classref) : baseRef.classref != null) {
			return false;
		}
		if (nametype != null ? !nametype.equals(baseRef.nametype) : baseRef.nametype != null) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result;
		result = (classref != null ? classref.hashCode() : 0);
		result = 31 * result + (nametype != null ? nametype.hashCode() : 0);
		return result;
	}

	protected String valueToString() {
		return String.format("classref={%s}, nametype={%s}", classref, nametype);
	}
}