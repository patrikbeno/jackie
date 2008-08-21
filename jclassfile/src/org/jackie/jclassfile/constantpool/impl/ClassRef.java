package org.jackie.jclassfile.constantpool.impl;

import org.jackie.jclassfile.constantpool.Constant;
import org.jackie.jclassfile.constantpool.CPEntryType;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.constantpool.Task;

import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;

/**
 * @author Patrik Beno
 */
public class ClassRef extends Constant {

	/*
CONSTANT_Class_info {
    	u1 tag;
    	u2 name_index;
    }
	 */

	Utf8 classname;

	ClassRef(ConstantPool pool) {
		super(pool);
	}

	ClassRef(ConstantPool pool, String classname) {
		super(pool);
		this.classname = factory().getUtf8(classname);
	}

	public CPEntryType type() {
		return CPEntryType.CLASS;
	}

	protected Task readConstantDataOrGetResolver(DataInput in) throws IOException {
		final int nameidx = in.readUnsignedShort();
		return new Task() {
			public void execute() throws IOException {
				classname = pool.getConstant(nameidx, Utf8.class); 
			}
		};
	}

	protected void writeConstantData(DataOutput out) throws IOException {
		classname.writeReference(out);
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ClassRef classRef = (ClassRef) o;

		if (classname != null ? !classname.equals(classRef.classname) : classRef.classname != null) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		return (classname != null ? classname.hashCode() : 0);
	}

}
