package org.jackie.jclassfile.attribute.dbg;

import org.jackie.jclassfile.attribute.AttributeProvider;
import org.jackie.jclassfile.attribute.AttributeSupport;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.constantpool.impl.Utf8;
import org.jackie.jclassfile.model.AttributeInfo;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Patrik Beno
 */
public class SourceFile extends AttributeInfo {

	static public class Provider implements AttributeProvider {
		public String name() {
			return "SourceFile";
		}
		public AttributeInfo createAttribute(AttributeSupport owner) {
			return new SourceFile(owner);
		}
	}

	/*
SourceFile_attribute {
    	u2 attribute_name_index;
    	u4 attribute_length;
    	u2 sourcefile_index;
    }   
    */

	Utf8 value;

	public SourceFile(AttributeSupport owner) {
		super(owner);
	}

	protected Task readConstantDataOrGetResolver(DataInput in, ConstantPool pool) throws IOException {
		readLength(in, 2);
		value = pool().getConstant(in.readUnsignedShort(), Utf8.class);
		return null;
	}

	protected void writeData(DataOutput out) throws IOException {
		writeLength(out, 2);
		value.writeReference(out);
	}
}
