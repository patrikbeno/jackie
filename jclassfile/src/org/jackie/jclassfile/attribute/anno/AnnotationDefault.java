package org.jackie.jclassfile.attribute.anno;

import org.jackie.jclassfile.attribute.AttributeProvider;
import org.jackie.jclassfile.attribute.AttributeSupport;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.utils.Assert;
import org.jackie.utils.XDataInput;
import org.jackie.utils.ByteArrayDataInput;
import org.jackie.utils.XDataOutput;

/**
 * @author Patrik Beno
 */
public class AnnotationDefault extends AttributeInfo {

	static public final String NAME = AnnotationDefault.class.getSimpleName();

	static public class Provider implements AttributeProvider {
		public String name() {
			return NAME;
		}
		public AttributeInfo createAttribute(AttributeSupport owner) {
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

	public AnnotationDefault(AttributeSupport owner) {
		super(owner);
	}

	public ElementValue value() {
		return value;
	}

	protected Task readConstantDataOrGetResolver(XDataInput in, final ConstantPool pool) {
		// just read data, don't parse now
		final int len = readLength(in);
		final byte[] bytes = new byte[len];
		in.readFully(bytes);

		return new Task() {
			public void execute() {
				XDataInput in = new ByteArrayDataInput(bytes);

				Tag tag = Tag.forId((char) in.readByte());
				ElementValue evalue = ElementValue.forTag(tag);
				evalue.init(this, null, tag);
				evalue.load(in, pool);

				value = evalue;
			}
		};
	}

	protected void writeData(XDataOutput out) {
		throw Assert.notYetImplemented(); // todo implement this
	}
}
