package org.jackie.jclassfile.attribute.std;

import org.jackie.jclassfile.attribute.AttributeProvider;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.impl.Utf8;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.model.ClassFileProvider;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Patrik Beno
 */
public class Signature extends AttributeInfo {

	static public class Provider implements AttributeProvider {
		public String name() {
			return "Signature";
		}
		public AttributeInfo createAttribute(ClassFileProvider owner) {
			return new Signature(owner);
		}
	}


	/*
Signature_attribute {
	u2 attribute_name_index
	u4 attribute_length
	u2 signature_index
}
	 */

	Utf8 value;

	public Signature(ClassFileProvider owner) {
		super(owner);
	}

	protected Task readConstantDataOrGetResolver(DataInput in) throws IOException {
		readLength(in, 2);
		value = pool().getConstant(in.readUnsignedShort(), Utf8.class);
		return null;
	}

	protected void writeData(DataOutput out) throws IOException {
		writeLength(out, 2);
		value.writeReference(out);
	}
}
