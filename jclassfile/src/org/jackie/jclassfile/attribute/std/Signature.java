package org.jackie.jclassfile.attribute.std;

import org.jackie.jclassfile.attribute.AttributeProvider;
import org.jackie.jclassfile.attribute.AttributeSupport;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.constantpool.impl.Utf8;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.utils.XDataInput;
import org.jackie.utils.XDataOutput;

/**
 * @author Patrik Beno
 */
public class Signature extends AttributeInfo {

	static public class Provider implements AttributeProvider {
		public String name() {
			return "Signature";
		}
		public AttributeInfo createAttribute(AttributeSupport owner) {
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

	public Signature(AttributeSupport owner) {
		super(owner);
	}

	protected Task readConstantDataOrGetResolver(XDataInput in, ConstantPool pool) {
		readLength(in, 2);
		value = pool.getConstant(in.readUnsignedShort(), Utf8.class);
		return null;
	}

	protected void writeData(XDataOutput out) {
		writeLength(out, 2);
		value.writeReference(out);
	}

	@Override
	public void registerConstants(ConstantPool pool) {
		super.registerConstants(pool);
		value = pool.register(value);
	}
}
