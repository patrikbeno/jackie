package org.jackie.jclassfile.constantpool.impl;

import org.jackie.jclassfile.constantpool.CPEntryType;
import org.jackie.jclassfile.constantpool.Constant;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.ConstantPool;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Patrik Beno
 */
public class DoubleRef extends Constant implements ValueProvider {

	double value;

	DoubleRef(ConstantPool pool) {
		super(pool);
	}

	DoubleRef(ConstantPool pool, double value) {
		super(pool);
		this.value = value;
	}

	public CPEntryType type() {
		return CPEntryType.DOUBLE;
	}

	public Object value() {
		return value;
	}

	public boolean isLongData() {
		return true;
	}

	protected Task readConstantDataOrGetResolver(DataInput in) throws IOException {
		value = in.readDouble();
		return null;
	}

	protected void writeConstantData(DataOutput out) throws IOException {
		out.writeDouble(value);
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DoubleRef doubleRef = (DoubleRef) o;

		if (Double.compare(doubleRef.value, value) != 0) return false;

		return true;
	}

	public int hashCode() {
		long temp = value != +0.0d ? Double.doubleToLongBits(value) : 0L;
		return (int) (temp ^ (temp >>> 32));
	}

	protected String valueToString() {
		return Double.toString(value);
	}
}