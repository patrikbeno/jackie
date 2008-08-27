package org.jackie.jclassfile.attribute.anno;

import org.jackie.jclassfile.attribute.AttributeProvider;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.model.ClassFileProvider;
import org.jackie.utils.Assert;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Patrik Beno
 */
public abstract class Annotations extends AttributeInfo {

	/*
RuntimeVisibleAnnotations_attribute {
u2 attribute_name_index;
u4 attribute_length;
u2 num_annotations;
annotation annotations[num_annotations];
}
	 */

	List<Annotation> annotations;

	public Annotations(ClassFileProvider owner) {
		super(owner);
	}

	protected Task loadAttribute(DataInput in) throws IOException {
		// just read data, don't parse now
		final int len = readLength(in);
		final byte[] bytes = new byte[len];
		in.readFully(bytes);

		return new Task() {
			public void execute() throws IOException {
				DataInput in = new DataInputStream(new ByteArrayInputStream(bytes));
				int count = in.readUnsignedShort();
				annotations = new ArrayList<Annotation>(count);
				while (count-- > 0) {
					Annotation a = new Annotation(Annotations.this);
					a.load(in);
					annotations.add(a);
				}
			}
		};
	}

	protected void writeData(DataOutput out) throws IOException {
		throw Assert.notYetImplemented(); // todo implement this
	}
}
