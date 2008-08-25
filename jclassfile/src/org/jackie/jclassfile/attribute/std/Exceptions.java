package org.jackie.jclassfile.attribute.std;

import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.model.ClassFileProvider;
import org.jackie.jclassfile.constantpool.impl.ClassRef;
import org.jackie.jclassfile.constantpool.impl.Utf8;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.util.Helper;

import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutput;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Patrik Beno
 */
public class Exceptions extends AttributeInfo {
   /*
Exceptions_attribute {
    	u2 attribute_name_index;
    	u4 attribute_length;
    	u2 number_of_exceptions;
    	u2 exception_index_table[number_of_exceptions];
    }   
    */

	List<ClassRef> exceptions;

	public Exceptions(ClassFileProvider owner) {
		super(owner);
	}

	protected Task readConstantDataOrGetResolver(DataInput in) throws IOException {
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
