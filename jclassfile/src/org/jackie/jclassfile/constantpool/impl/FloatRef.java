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
public class FloatRef extends Constant {

	float value;

	FloatRef(ConstantPool pool) {
		super(pool);
	}

	FloatRef(ConstantPool pool, float value) {
		super(pool);
		this.value = value;
	}

	public CPEntryType type() {
		return CPEntryType.FLOAT;
	}

	protected Task readConstantDataOrGetResolver(DataInput in) throws IOException {
		value = in.readFloat();
		return null;
	}

	protected void writeConstantData(DataOutput out) throws IOException {
		out.writeFloat(value);
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FloatRef floatRef = (FloatRef) o;

		if (Float.compare(floatRef.value, value) != 0) return false;

		return true;
	}

	public int hashCode() {
		return (value != +0.0f ? Float.floatToIntBits(value) : 0);
	}

	protected String valueToString() {
		return Float.toString(value);
	}
}