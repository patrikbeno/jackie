package org.jackie.jclassfile.code;

import org.jackie.utils.Assert;

import java.io.DataOutput;

/**
 * @author Patrik Beno
 */
public class Insn {

	public int size() {
		return 0;
	}

	public void write(DataOutput out) {
		throw Assert.notYetImplemented(); // todo implement this
	}
}
