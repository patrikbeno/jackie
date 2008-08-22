package org.jackie.jclassfile.model;

import static org.jackie.utils.Assert.doAssert;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Patrik Beno
 */
public abstract class Base {

	abstract
	public void load(DataInput in) throws IOException;

	abstract
	public void save(DataOutput out) throws IOException;


	protected int readLength(DataInput in) throws IOException {
		return readLength(in, null);
	}

	protected int readLength(DataInput in, Integer expected) throws IOException {
		int len = in.readUnsignedShort();
		if (expected != null) {
			doAssert(len == expected, "Invalid length: %s. Expected: %s", len, expected);
		}
		return len;
	}

	protected void writeLength(DataOutput out, int length) throws IOException {
		out.writeShort(length);
	}

	protected void assertLength(int actual, int expected) {
		doAssert(actual==expected, "Actual and expected lengths do not match (actual=%s, expected=%s)", actual, expected);
	}
}
