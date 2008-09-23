package org.jackie.jclassfile.constantpool.impl;

import org.jackie.jclassfile.constantpool.Constant;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.constantpool.Task;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

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

	BaseRef(ConstantPool pool) {
		super(pool);
	}

	BaseRef(ConstantPool pool, String clsname, String name, String type) {
		this(pool);
		classref = factory().getClassRef(clsname);
		nametype = factory().getNameAndType(name, type);
	}

	public ClassRef classref() {
		return classref;
	}

	public NameAndType nametype() {
		return nametype;
	}

	protected Task readConstantDataOrGetResolver(DataInput in) throws IOException {
		final int classidx = in.readUnsignedShort();
		final int nametypeidx = in.readUnsignedShort();
		return new Task() {
			public void execute() throws IOException {
				classref = pool.getConstant(classidx, ClassRef.class);
				nametype = pool.getConstant(nametypeidx, NameAndType.class);
			}
		};
	}

	protected void writeConstantData(DataOutput out) throws IOException {
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