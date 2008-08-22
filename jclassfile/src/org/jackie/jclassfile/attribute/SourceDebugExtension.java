package org.jackie.jclassfile.attribute;

import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.model.ClassFileProvider;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Patrik Beno
 */
public class SourceDebugExtension extends AttributeInfo {

	/*
SourceDebugExtension_attribute {
       u2 attribute_name_index;
       u4 attribute_length;
       u1 debug_extension[attribute_length];
    }
	 */

	String value;

	public SourceDebugExtension(ClassFileProvider owner, DataInput in) throws IOException {
		super(owner, in);
	}

	protected Task readConstantDataOrGetResolver(DataInput in) throws IOException {
		readLength(in);
		value = in.readUTF();
		return null;
	}

	protected void writeData(DataOutput out) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream tmpout = new DataOutputStream(baos);
		tmpout.writeUTF(value);
		tmpout.close();
		byte[] bytes = baos.toByteArray();

		writeLength(out, bytes.length);
		out.write(bytes);
	}
}
