package org.jackie.jclassfile.attribute.anno;

import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.model.ClassFileProvider;
import org.jackie.jclassfile.constantpool.impl.Utf8;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.ConstantPool;
import static org.jackie.jclassfile.constantpool.ConstantPool.constantPool;
import org.jackie.jclassfile.attribute.AttributeProvider;
import org.jackie.utils.Assert;

import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutput;

/**
 * @author Patrik Beno
 */
public class AnnotationDefault extends AttributeInfo {

	static public class Provider implements AttributeProvider {
		public String name() {
			return "AnnotationDefault";
		}
		public AttributeInfo createAttribute(ClassFileProvider owner) {
			return new AnnotationDefault(owner);
		}
	}

	/*
AnnotationDefault_attribute {
	attribute_name_index
	attribute_length
	element_value default_value
}
	 */

	ElementValue value;

	public AnnotationDefault(ClassFileProvider owner) {
		super(owner);
	}

	public ElementValue value() {
		return value;
	}

	protected Task readConstantDataOrGetResolver(DataInput in) throws IOException {
		readLength(in);

		ConstantPool pool = constantPool();
		Utf8 ename = pool.getConstant(in.readUnsignedShort(), Utf8.class);
		Tag tag = Tag.forId((char) in.readByte());

		ElementValue evalue = ElementValue.forTag(tag);
		evalue.init(this,  ename, tag);
		evalue.load(in);

		this.value = evalue;

		return null;
	}

	protected void writeData(DataOutput out) throws IOException {
		throw Assert.notYetImplemented(); // todo implement this
	}
}
