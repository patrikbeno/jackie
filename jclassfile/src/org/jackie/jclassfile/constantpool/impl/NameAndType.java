package org.jackie.jclassfile.constantpool.impl;

import org.jackie.jclassfile.constantpool.CPEntryType;
import org.jackie.jclassfile.constantpool.Constant;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.constantpool.Task;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Patrik Beno
 */
public class NameAndType extends Constant {

	/*
CONSTANT_NameAndType_info {
    	u1 tag;
    	u2 name_index;
    	u2 descriptor_index;
    }
	 */

	Utf8 name;
	Utf8 type;

	NameAndType(ConstantPool pool) {
		super(pool);
	}

	NameAndType(ConstantPool pool, String name, String type) {
		super(pool);
		this.name = factory().getUtf8(name);
		this.type = factory().getUtf8(type);
	}

	public CPEntryType type() {
		return CPEntryType.NAME_AND_TYPE; 
	}

	public String name() {
		return name.value();
	}

	public String stype() {
		return type.value();
	}

	protected Task readConstantDataOrGetResolver(DataInput in) throws IOException {
		final int nameidx = in.readUnsignedShort();
		final int descidx = in.readUnsignedShort();
		return new Task() {
			public void execute() throws IOException {
				name = pool.getConstant(nameidx, Utf8.class);
				type = pool.getConstant(descidx, Utf8.class);
			}
		};
	}

	protected void writeConstantData(DataOutput out) throws IOException {
		name.writeReference(out);
		type.writeReference(out);
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		NameAndType that = (NameAndType) o;

		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (type != null ? !type.equals(that.type) : that.type != null) return false;

		return true;
	}

	public int hashCode() {
		int result;
		result = (name != null ? name.hashCode() : 0);
		result = 31 * result + (type != null ? type.hashCode() : 0);
		return result;
	}

	protected String valueToString() {
		return String.format(
				"name={%s}, type={%s}",
				name != null ? name.value() : null,
				type != null ? type.value() : null
		);
	}
}
