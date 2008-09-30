package org.jackie.jclassfile.attribute.dbg;

import org.jackie.jclassfile.attribute.AttributeProvider;
import org.jackie.jclassfile.attribute.AttributeSupport;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.model.AttributeInfo;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Patrik Beno
 */
public class SourceDebugExtension extends AttributeInfo {

	static public class Provider implements AttributeProvider {
		public String name() {
			return "SourceDebugExtension";
		}
		public AttributeInfo createAttribute(AttributeSupport owner) {
			return new SourceDebugExtension(owner);
		}
	}


	/*
SourceDebugExtension_attribute {
       u2 attribute_name_index;
       u4 attribute_length;
       u1 debug_extension[attribute_length];
    }
	 */

	String value;

	public SourceDebugExtension(AttributeSupport owner) {
		super(owner);
	}

	protected Task readConstantDataOrGetResolver(DataInput in, ConstantPool pool) throws IOException {
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
