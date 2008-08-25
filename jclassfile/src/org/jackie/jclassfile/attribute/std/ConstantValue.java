package org.jackie.jclassfile.attribute.std;

import org.jackie.jclassfile.constantpool.Constant;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.model.ClassFileProvider;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Patrik Beno
 */
public class ConstantValue extends AttributeInfo {
   /*
ConstantValue_attribute {
    	u2 attribute_name_index;
    	u4 attribute_length;
    	u2 constantvalue_index;
    }   
    */

	Constant constant; // IntegerRef, LongRef, FloatRef, DoubleRef, StringRef

	public ConstantValue(ClassFileProvider owner) {
		super(owner);
	}

	protected Task readConstantDataOrGetResolver(DataInput in) throws IOException {
		readLength(in, 2);
		constant = owner.classFile().pool().getConstant(in.readUnsignedShort(), Constant.class);
		return null;
	}

	protected void writeData(DataOutput out) throws IOException {
		writeLength(out, 2);
		constant.writeReference(out);
	}
}
