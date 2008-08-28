package org.jackie.jclassfile.constantpool.impl;

import org.jackie.jclassfile.constantpool.CPEntryType;
import org.jackie.jclassfile.constantpool.Constant;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.constantpool.Task;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Patrik Beno
 */
public class LongRef extends Constant implements ValueProvider {

	long value;

	LongRef(ConstantPool pool) {
		super(pool);
	}

	LongRef(ConstantPool pool, long value) {
		super(pool);
		this.value = value;
	}

	public CPEntryType type() {
		return CPEntryType.LONG;
	}

	public Object value() {
		return value;
	}

	public boolean isLongData() {
		return true;
	}

	protected Task readConstantDataOrGetResolver(DataInput in) throws IOException {
		value = in.readLong();
		return null;
	}

	protected void writeConstantData(DataOutput out) throws IOException {
		out.writeLong(value);
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		LongRef longRef = (LongRef) o;

		if (value != longRef.value) return false;

		return true;
	}

	public int hashCode() {
		return (int) (value ^ (value >>> 32));
	}

	protected String valueToString() {
		return Long.toString(value);
	}
}