package org.jackie.jclassfile.attribute.anno;

import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.attribute.AttributeSupport;
import org.jackie.utils.Assert;
import org.jackie.utils.XDataInput;
import org.jackie.utils.XDataOutput;

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

	protected Task readConstantDataOrGetResolver(XDataInput in, ConstantPool pool) {
		throw Assert.notYetImplemented(); // todo implement this
	}

	protected void writeData(XDataOutput out) {
		throw Assert.notYetImplemented(); // todo implement this
	}
}
