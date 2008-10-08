package org.jackie.jclassfile.attribute.anno;

import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.attribute.AttributeSupport;
import org.jackie.utils.Assert;
import org.jackie.utils.IOHelper;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

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

	public Annotations(AttributeSupport owner) {
		super(owner);
	}

	public List<Annotation> getAnnotations() {
		return annotations;
	}

	protected Task readConstantDataOrGetResolver(DataInput in, ConstantPool pool) throws IOException {
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
					a.load(in, pool());
					annotations.add(a);
				}
			}
		};
	}

	protected void writeData(DataOutput out) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutput tmpout = new DataOutputStream(baos);

		tmpout.writeShort(annotations.size()); // count
		for (Annotation a : annotations) {
			a.save(tmpout);
		}

		IOHelper.close(tmpout);
		byte[] bytes = baos.toByteArray();

		writeLength(out, bytes.length);
		out.write(bytes);
	}
}
