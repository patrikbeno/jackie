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
public class FloatRef extends Constant implements ValueProvider {

	static public final Loader LOADER = new Loader() {
		protected Constant create() {
			return new FloatRef(); 
		}
	};

	static public FloatRef create(float value) {
		return new FloatRef(value);
	}

	float value;

	protected FloatRef() {
	}

	protected FloatRef(float value) {
		this.value = value;
	}

	public CPEntryType type() {
		return CPEntryType.FLOAT;
	}

	public Object value() {
		return value;
	}

	protected Task readConstantDataOrGetResolver(XDataInput in, ConstantPool pool) {
		value = in.readFloat();
		return null;
	}

	protected void writeConstantData(XDataOutput out) {
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