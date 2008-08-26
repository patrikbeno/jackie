package org.jackie.jclassfile.attribute.std;

import org.jackie.jclassfile.attribute.AttributeProvider;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.model.ClassFileProvider;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Patrik Beno
 */
public class Deprecated extends AttributeInfo {

	static public class Provider implements AttributeProvider {
		public String name() {
			return "Deprecated";
		}
		public AttributeInfo createAttribute(ClassFileProvider owner) {
			return new Deprecated(owner);
		}
	}

	/*
Deprecated_attribute {
    	u2 attribute_name_index;
    	u4 attribute_length;
    }   
    */

	public Deprecated(ClassFileProvider owner) {
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
