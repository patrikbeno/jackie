package org.jackie.jclassfile.constantpool.impl;

import org.jackie.jclassfile.constantpool.CPEntryType;
import org.jackie.jclassfile.constantpool.Constant;
import org.jackie.jclassfile.constantpool.Task;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.utils.XDataInput;
import org.jackie.utils.XDataOutput;

/**
 * @author Patrik Beno
 */
public class DoubleRef extends Constant implements ValueProvider {

	static public final Loader LOADER = new Loader() {
		protected Constant create() {
			return new DoubleRef();
		}
	};

	static public DoubleRef create(double value) {
		return new DoubleRef(value);
	}

	double value;

	protected DoubleRef() {
	}

	protected DoubleRef(double value) {
		this.value = value;
	}

	public CPEntryType type() {
		return CPEntryType.DOUBLE;
	}

	public Double value() {
		return value;
	}

	public boolean isLongData() {
		return true;
	}

	protected Task readConstantDataOrGetResolver(XDataInput in, ConstantPool pool) {
		value = in.readDouble();
		return null;
	}

	protected void writeConstantData(XDataOutput out) {
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