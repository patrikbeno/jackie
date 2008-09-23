package org.jackie.jclassfile.code;

import org.jackie.jclassfile.constantpool.Constant;

import java.io.IOException;
import java.io.DataOutput;

/**
 * @author Patrik Beno
*/
abstract class PoolRefInstruction<T extends Constant> implements Instruction {

	int opcode;
	T constant;

	protected PoolRefInstruction(int opcode, T constant) {
		this.opcode = opcode;
		this.constant = constant;
	}

	public int opcode() {
		return opcode;
	}

	public int size() {
		return 3; // 1: opcode; 2-3: pool index
	}

	public void save(DataOutput out) throws IOException {
		out.writeByte(opcode);
		constant.writeReference(out);
	}

	public String toString() {
		return String.format("%s %s", Opcode.forOpcode(opcode), operandToString());
	}

	protected String operandToString() {
		return constant.toString();
	}
}
