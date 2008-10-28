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
public class IntegerRef extends Constant implements ValueProvider {

	static public final Loader LOADER = new Loader() {
		protected Constant create() {
			return new IntegerRef(); 
		}
	};

	static public IntegerRef create(int value) {
		return new IntegerRef(value);
	}

	int value;

	protected IntegerRef() {
	}

	protected IntegerRef(int value) {
		this.value = value;
	}

	public CPEntryType type() {
		return CPEntryType.INTEGER;
	}

	public Object value() {
		return value;
	}

	protected Task readConstantDataOrGetResolver(XDataInput in, ConstantPool pool) {
		value = in.readInt();
		return null;
	}

	protected void writeConstantData(XDataOutput out) {
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

	protected String valueToString() {
		return Integer.toString(value);
	}
}