package org.jackie.jclassfile.attribute.anno;

import org.jackie.jclassfile.attribute.AttributeProvider;
import org.jackie.jclassfile.constantpool.ConstantPool;
import static org.jackie.jclassfile.constantpool.ConstantPool.constantPool;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.impl.Utf8;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.model.ClassFileProvider;
import org.jackie.utils.Assert;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;

/**
 * @author Patrik Beno
 */
public class AnnotationDefault extends AttributeInfo {

	static public final String NAME = AnnotationDefault.class.getSimpleName();

	static public class Provider implements AttributeProvider {
		public String name() {
			return NAME;
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
		// just read data, don't parse now
		final int len = readLength(in);
		final byte[] bytes = new byte[len];
		in.readFully(bytes);

		return new Task() {
			public void execute() throws IOException {
				DataInput in = new DataInputStream(new ByteArrayInputStream(bytes));

				Tag tag = Tag.forId((char) in.readByte());
				ElementValue evalue = ElementValue.forTag(tag);
				evalue.init(this,  null, tag);
				evalue.load(in);

				value = evalue;
			}
		};
	}

	protected void writeData(DataOutput out) throws IOException {
		throw Assert.notYetImplemented(); // todo implement this
	}
}
