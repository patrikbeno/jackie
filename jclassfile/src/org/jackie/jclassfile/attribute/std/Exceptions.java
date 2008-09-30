package org.jackie.jclassfile.attribute.std;

import org.jackie.jclassfile.attribute.AttributeProvider;
import org.jackie.jclassfile.attribute.AttributeSupport;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.constantpool.impl.ClassRef;
import org.jackie.jclassfile.model.AttributeInfo;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class Exceptions extends AttributeInfo {

	static public class Provider implements AttributeProvider {
		public String name() {
			return "Exceptions";
		}
		public AttributeInfo createAttribute(AttributeSupport owner) {
			return new Exceptions(owner);
		}
	}

	/*
Exceptions_attribute {
    	u2 attribute_name_index;
    	u4 attribute_length;
    	u2 number_of_exceptions;
    	u2 exception_index_table[number_of_exceptions];
    }   
    */

	List<ClassRef> exceptions;

	public Exceptions(AttributeSupport owner) {
		super(owner);
	}

	protected Task readConstantDataOrGetResolver(DataInput in, ConstantPool pool) throws IOException {
		readLength(in);
		int count = in.readUnsignedShort();
		exceptions = new ArrayList<ClassRef>(count);
		while (count-- > 0) {
			ClassRef cref = pool().getConstant(in.readUnsignedShort(), ClassRef.class);
			exceptions.add(cref);
		}
		return null;
	}

	protected void writeData(DataOutput out) throws IOException {
		writeLength(out, 2+exceptions.size()*2); 
		out.writeShort(exceptions.size());
		for (ClassRef cref : exceptions) {
			cref.writeReference(out);
		}
	}
}
