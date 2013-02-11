package org.jackie.jclassfile.attribute.anno;

import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.attribute.AttributeSupport;
import org.jackie.utils.XDataInput;
import org.jackie.utils.ByteArrayDataInput;
import org.jackie.utils.XDataOutput;
import org.jackie.utils.ByteArrayDataOutput;

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

	protected Task readConstantDataOrGetResolver(XDataInput in, final ConstantPool pool) {
		// just read data, don't parse now
		final int len = readLength(in);
		final byte[] bytes = new byte[len];
		in.readFully(bytes);

		return new Task() {
			public void execute() {
				XDataInput in = new ByteArrayDataInput(bytes);

				int count = in.readUnsignedShort();
				annotations = new ArrayList<Annotation>(count);
				while (count-- > 0) {
					Annotation a = new Annotation(Annotations.this);
					a.load(in, pool);
					annotations.add(a);
				}
			}
		};
	}

	protected void writeData(XDataOutput out) {
		ByteArrayDataOutput tmpout = new ByteArrayDataOutput();

		tmpout.writeShort(annotations.size()); // count
		for (Annotation a : annotations) {
			a.save(tmpout);
		}
		byte[] bytes = tmpout.toByteArray();

		writeLength(out, bytes.length);
		out.write(bytes);
	}

	@Override
	public void registerConstants(ConstantPool pool) {
		super.registerConstants(pool);
		for (Annotation a : annotations) {
			a.registerConstants(pool);
		}
	}
}
