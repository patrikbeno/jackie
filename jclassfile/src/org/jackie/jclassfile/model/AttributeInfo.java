package org.jackie.jclassfile.model;

import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.impl.Utf8;
import org.jackie.jclassfile.attribute.AttributeSupport;
import static org.jackie.utils.Assert.doAssert;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Patrik Beno
 */
public abstract class AttributeInfo extends Base {
	/*
	attribute_info {
			 u2 attribute_name_index;
			 u4 attribute_length;
			 u1 info[attribute_length];
		 }
		 */

	protected AttributeSupport owner;

	protected Utf8 name;
	Task resolver;

	protected AttributeInfo(AttributeSupport owner, Utf8 name) {
		this.owner = owner;
		this.name = name;
	}

	protected AttributeInfo(AttributeSupport owner) {
		this.owner = owner;
		this.name = pool().factory().getUtf8(getClass().getSimpleName());
	}

	public String name() {
		return name.value();
	}

	protected ConstantPool pool() {
		return owner.constantPool();
	}

	public void load(DataInput in) throws IOException {
		// name is expected to be loaded by attribute resolver (and passed in constructor)
		resolver = readConstantDataOrGetResolver(in, pool());
		if (resolver != null) { // todo debug
			resolver.execute();
		}
	}

	public void save(DataOutput out) throws IOException {
		name.writeReference(out);
		writeData(out);
	}

	protected int readLength(DataInput in, Integer expected) throws IOException {
		int len = in.readInt();
		if (expected != null) {
			doAssert(len == expected, "Invalid length: %s. Expected: %s", len, expected);
		}
		return len;
	}

	protected void writeLength(DataOutput out, int length) throws IOException {
		out.writeInt(length);
	}

	protected abstract Task readConstantDataOrGetResolver(DataInput in, ConstantPool pool) throws IOException;

	protected abstract void writeData(DataOutput out) throws IOException;

	public String toString() {
		return String.format("%s {%s}", name.value(), valueToString());
	}

	protected String valueToString() {
		return "";
	}

}
