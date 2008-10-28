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
public class Utf8 extends Constant implements ValueProvider {

	/*
CONSTANT_Utf8_info {
    	u1 tag;
    	u2 length;
    	u1 bytes[length];
    }
	 */

	static public final Loader LOADER = new Loader() {
		protected Constant create() {
			return new Utf8();  
		}
	};

	static public Utf8 create(String value) {
		return new Utf8(value);
	}

	String value;

	protected Utf8() {
	}

	protected Utf8(String value) {
		this.value = value;
	}

	public CPEntryType type() {
		return CPEntryType.UTF8;
	}

	public String value() {
		return value;
	}

	protected Task readConstantDataOrGetResolver(XDataInput in, ConstantPool pool) {
		value = in.readUTF();
		return null;
	}

	protected void writeConstantData(XDataOutput out) {
		out.writeUTF(value);
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Utf8 utf8 = (Utf8) o;

		if (value != null ? !value.equals(utf8.value) : utf8.value != null) return false;

		return true;
	}

	public int hashCode() {
		return (value != null ? value.hashCode() : 0);
	}

	protected String valueToString() {
		return value;
	}

}
