package org.jackie.jclassfile.attribute.std;

import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.model.ClassFileProvider;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Patrik Beno
 */
public class Synthetic extends AttributeInfo {

	/*
Synthetic_attribute {
    	u2 attribute_name_index;
    	u4 attribute_length;
    }   
    */

	public Synthetic(ClassFileProvider owner) {
		super(owner);
	}

	protected Task readConstantDataOrGetResolver(DataInput in) throws IOException {
		readLength(in, 0);
		return null;
	}

	protected void writeData(DataOutput out) throws IOException {
		writeLength(out, 0);
	}
}
