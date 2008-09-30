package org.jackie.jclassfile.attribute;

import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.constantpool.impl.Utf8;
import org.jackie.jclassfile.model.AttributeInfo;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Patrik Beno
 */
public class GenericAttribute extends AttributeInfo {
	/*
	attribute_info {
			 u2 attribute_name_index;
			 u4 attribute_length;
			 u1 info[attribute_length];
		 }
		 */

	byte[] data;

	public GenericAttribute(AttributeSupport owner, Utf8 name) {
		super(owner, name);
	}

	public int length() {
		return data.length;
	}

	public byte[] data() {
		return data;
	}

	public void data(byte[] bytes) {
		this.data = bytes;
	}

	protected Task readConstantDataOrGetResolver(DataInput in, ConstantPool pool) throws IOException {
		data = new byte[readLength(in)];
		in.readFully(data);
		return null;
	}

	protected void writeData(DataOutput out) throws IOException {
		writeLength(out, data.length);
		out.write(data);
	}

	protected String valueToString() {
		return String.format("%s bytes", data != null ? data.length : "?");
	}
}