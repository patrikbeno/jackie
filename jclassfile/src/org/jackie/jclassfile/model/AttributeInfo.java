package org.jackie.jclassfile.model;

import org.jackie.jclassfile.constantpool.impl.Utf8;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.ConstantPool;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Patrik Beno
 */
public abstract class AttributeInfo extends Base {
	/*
	attribute_info {
			 u2 attribute_name_index;
			 u4 attribute_length;
			 u1 info[attribute_length];
		 }
		 */

	protected ClassFileProvider owner;

	protected Utf8 name;
	Task resolver;

	protected AttributeInfo(ClassFileProvider owner) {
		this.owner = owner;
	}

	public AttributeInfo(ClassFileProvider owner, DataInput in) throws IOException {
		this(owner);
		load(in);
	}

	public void load(DataInput in) throws IOException {
		name = owner.classFile().pool().getConstant(in.readUnsignedShort(), Utf8.class);
		resolver = readConstantDataOrGetResolver(in);
	}

	public void save(DataOutput out) throws IOException {
		name.writeReference(out);
		writeData(out);
	}

	protected abstract Task readConstantDataOrGetResolver(DataInput in) throws IOException;

	protected abstract void writeData(DataOutput out) throws IOException;

	protected ConstantPool pool() {
		return owner.classFile().pool();
	}

	public String toString() {
		return String.format("Attribute(%s: {%s})", name, valueToString());
	}

	protected String valueToString() {
		return "";
	}

}
