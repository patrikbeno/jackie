package org.jackie.jclassfile.attribute.std;

import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.impl.ClassRef;
import org.jackie.jclassfile.constantpool.impl.NameAndType;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.model.ClassFileProvider;
import org.jackie.jclassfile.attribute.AttributeProvider;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Patrik Beno
 */
public class EnclosingMethod extends AttributeInfo {

	static public class Provider implements AttributeProvider {
		public String name() {
			return "EnclosingMethod";
		}
		public AttributeInfo createAttribute(ClassFileProvider owner) {
			return new EnclosingMethod(owner);
		}
	}


	/*
EnclosingZethod_attributeC{
	u2 attribute_name_index
	u4 attribute_length
	u2 class_index
	u2 method_index
}
	 */

	ClassRef cls;
	NameAndType method;

	public EnclosingMethod(ClassFileProvider owner) {
		super(owner);
	}

	protected Task readConstantDataOrGetResolver(DataInput in) throws IOException {
		readLength(in, 4);
		cls = pool().getConstant(in.readUnsignedShort(), ClassRef.class);
		method = pool().getConstant(in.readUnsignedShort(), NameAndType.class);
		return null;
	}

	protected void writeData(DataOutput out) throws IOException {
		writeLength(out, 4);
		cls.writeReference(out);
		method.writeReference(out);
	}
}
