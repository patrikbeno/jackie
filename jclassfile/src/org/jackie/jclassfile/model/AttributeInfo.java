package org.jackie.jclassfile.model;

import org.jackie.utils.Assert;
import org.jackie.jclassfile.constantpool.impl.Utf8;
import org.jackie.jclassfile.constantpool.ConstantPool;

import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;

/**
 * @author Patrik Beno
 */
public class AttributeInfo extends Base {
	/*
	attribute_info {
			 u2 attribute_name_index;
			 u4 attribute_length;
			 u1 info[attribute_length];
		 }
		 */

	ClassFileProvider owner;
	Utf8 name;
	byte[] data;

	public AttributeInfo(ClassFileProvider owner, DataInput in) throws IOException {
		this.owner = owner;
		load(in);
	}

	public void load(DataInput in) throws IOException {
		name = owner.classFile().pool().getConstant(in.readUnsignedShort(), Utf8.class);
		data = new byte[in.readInt()];
		in.readFully(data);
	}

	public void save(DataOutput out) throws IOException {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public String toString() {
		return String.format("Attribute(%s, %s bytes)", name, data.length);
	}
}
