package org.jackie.jclassfile.attribute.anno;

import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.attribute.AttributeSupport;
import org.jackie.utils.Assert;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Patrik Beno
 */
public abstract class ParameterAnnotations extends AttributeInfo {

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

	public ParameterAnnotations(AttributeSupport owner) {
		super(owner);
	}

	protected Task readConstantDataOrGetResolver(DataInput in, ConstantPool pool) throws IOException {
		throw Assert.notYetImplemented(); // todo implement this
	}

	protected void writeData(DataOutput out) throws IOException {
		throw Assert.notYetImplemented(); // todo implement this
	}
}
