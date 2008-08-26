package org.jackie.jclassfile.attribute.anno;

import org.jackie.jclassfile.attribute.AttributeProvider;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.model.ClassFileProvider;
import org.jackie.utils.Assert;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Patrik Beno
 */
public class RuntimeVisibleParameterAnnotations extends AttributeInfo {

	static public class Provider implements AttributeProvider {
		public String name() {
			return "RuntimeVisibleParameterAnnotations";
		}
		public AttributeInfo createAttribute(ClassFileProvider owner) {
			return new RuntimeVisibleParameterAnnotations(owner);
		}
	}

	/*
RuntimeVisibleParameterAnnotations_attribute {
     u2 attribute_name_index;
     u4 attribute_length;
     u1 num_parameters;
     {
       u2 num_annotations;
       annotation annotations[num_annotations];
     } parameter_annotations[num_parameters];
   }
	 */

	public RuntimeVisibleParameterAnnotations(ClassFileProvider owner) {
		super(owner);
	}

	protected Task readConstantDataOrGetResolver(DataInput in) throws IOException {
		throw Assert.notYetImplemented(); // todo implement this
	}

	protected void writeData(DataOutput out) throws IOException {
		throw Assert.notYetImplemented(); // todo implement this
	}
}
