package org.jackie.jclassfile.model;

import static org.jackie.utils.Assert.doAssert;
import org.jackie.utils.XDataInput;
import org.jackie.utils.XDataOutput;
import org.jackie.jclassfile.constantpool.ConstantPool;

import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Patrik Beno
 */
public abstract class Base {

	abstract
	public void load(XDataInput in, ConstantPool pool);

	abstract
	public void save(XDataOutput out);

	protected int readLength(XDataInput in) {
		return readLength(in, null);
	}

	protected int readLength(XDataInput in, Integer expected) {
		int len = in.readUnsignedShort();
		if (expected != null) {
			doAssert(len == expected, "Invalid length: %s. Expected: %s", len, expected);
		}
		return len;
	}

	protected void writeLength(XDataOutput out, int length) {
		out.writeShort(length);
	}

	protected void assertLength(int actual, int expected) {
		doAssert(actual==expected, "Actual and expected lengths do not match (actual=%s, expected=%s)", actual, expected);
	}
}
