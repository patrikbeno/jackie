package org.jackie.jclassfile.attribute.std;

import org.jackie.jclassfile.attribute.AttributeProvider;
import org.jackie.jclassfile.attribute.AttributeSupport;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.utils.XDataInput;
import org.jackie.utils.XDataOutput;

/**
 * @author Patrik Beno
 */
public class Synthetic extends AttributeInfo {

	static public class Provider implements AttributeProvider {
		public String name() {
			return "Synthetic";
		}
		public AttributeInfo createAttribute(AttributeSupport owner) {
			return new Synthetic(owner);
		}
	}


	/*
Synthetic_attribute {
    	u2 attribute_name_index;
    	u4 attribute_length;
    }   
    */

	public Synthetic(AttributeSupport owner) {
		super(owner);
	}

	protected Task readConstantDataOrGetResolver(XDataInput in, ConstantPool pool) {
		readLength(in, 0);
		return null;
	}

	protected void writeData(XDataOutput out) {
		writeLength(out, 0);
	}
}
