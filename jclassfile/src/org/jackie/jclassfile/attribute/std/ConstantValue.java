package org.jackie.jclassfile.attribute.std;

import org.jackie.jclassfile.attribute.AttributeProvider;
import org.jackie.jclassfile.attribute.AttributeSupport;
import org.jackie.jclassfile.constantpool.Constant;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.utils.XDataInput;
import org.jackie.utils.XDataOutput;

/**
 * @author Patrik Beno
 */
public class ConstantValue extends AttributeInfo {

	static public class Provider implements AttributeProvider {
		public String name() {
			return "ConstantValue";
		}
		public AttributeInfo createAttribute(AttributeSupport owner) {
			return new ConstantValue(owner);
		}
	}

	/*
ConstantValue_attribute {
    	u2 attribute_name_index;
    	u4 attribute_length;
    	u2 constantvalue_index;
    }   
    */

	Constant constant; // IntegerRef, LongRef, FloatRef, DoubleRef, StringRef

	public ConstantValue(AttributeSupport owner) {
		super(owner);
	}

	protected Task readConstantDataOrGetResolver(XDataInput in, ConstantPool pool) {
		readLength(in, 2);
		constant = pool.getConstant(in.readUnsignedShort(), Constant.class);
		return null;
	}

	protected void writeData(XDataOutput out) {
		writeLength(out, 2);
		constant.writeReference(out);
	}

	@Override
	public void registerConstants(ConstantPool pool) {
		super.registerConstants(pool);
		constant = pool.register(constant);
	}
}
