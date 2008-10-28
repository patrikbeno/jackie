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
public class LongRef extends Constant implements ValueProvider {

	static public final Loader LOADER = new Loader() {
		protected Constant create() {
			return new LongRef(); 
		}
	};

	static public LongRef create(long value) {
		return new LongRef(value);
	}

	long value;

	protected LongRef() {
	}

	protected LongRef(long value) {
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

	protected Task readConstantDataOrGetResolver(XDataInput in, ConstantPool pool) {
		value = in.readLong();
		return null;
	}

	protected void writeConstantData(XDataOutput out) {
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