package org.jackie.jclassfile.attribute.dbg;

import org.jackie.jclassfile.attribute.AttributeProvider;
import org.jackie.jclassfile.attribute.AttributeSupport;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.utils.XDataInput;
import org.jackie.utils.XDataOutput;
import org.jackie.utils.ByteArrayDataOutput;

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

	protected Task readConstantDataOrGetResolver(XDataInput in, ConstantPool pool) {
		readLength(in);
		value = in.readUTF();
		return null;
	}

	protected void writeData(XDataOutput out) {
		ByteArrayDataOutput bado = new ByteArrayDataOutput();
		bado.writeUTF(value);
		byte[] bytes = bado.toByteArray();

		writeLength(out, bytes.length);
		out.write(bytes);
	}
}
