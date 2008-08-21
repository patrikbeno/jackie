package org.jackie.jclassfile.constantpool.impl;

import org.jackie.jclassfile.constantpool.Constant;
import org.jackie.jclassfile.constantpool.CPEntryType;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.utils.Assert;

import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;

/**
 * @author Patrik Beno
 */
public class IntegerRef extends Constant {

	int value;

	IntegerRef(ConstantPool pool) {
		super(pool);
	}

	IntegerRef(ConstantPool pool, int value) {
		super(pool);
		this.value = value;
	}

	public CPEntryType type() {
		return CPEntryType.INTEGER;
	}

	protected Task readConstantDataOrGetResolver(DataInput in) throws IOException {
		value = in.readInt();
		return null;
	}

	protected void writeConstantData(DataOutput out) throws IOException {
		out.writeInt(value);
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		IntegerRef that = (IntegerRef) o;

		if (value != that.value) return false;

		return true;
	}

	public int hashCode() {
		return value;
	}
}