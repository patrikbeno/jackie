package org.jackie.jclassfile.attribute;

import org.jackie.jclassfile.constantpool.impl.Utf8;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.model.Base;
import org.jackie.jclassfile.model.ClassFileProvider;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.utils.Assert;

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

	public GenericAttribute(ClassFileProvider owner, byte[] data) {
		super(owner);
		this.data = data;
	}

	public GenericAttribute(ClassFileProvider owner, DataInput in) throws IOException {
		super(owner, in);
	}

	protected Task readConstantDataOrGetResolver(DataInput in) throws IOException {
		data = new byte[readLength(in)];
		in.readFully(data);
		return null;
	}

	protected void writeData(DataOutput out) throws IOException {
		writeLength(out, data.length);
		out.write(data);
	}

	protected String valueToString() {
		return String.format("%s bytes", data.length);
	}
}