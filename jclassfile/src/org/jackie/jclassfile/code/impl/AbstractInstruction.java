package org.jackie.jclassfile.code.impl;

import org.jackie.jclassfile.code.Instruction;
import org.jackie.jclassfile.code.CodeParser;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.utils.ChainImpl;
import org.jackie.utils.XDataOutput;
import org.jackie.utils.Log;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Patrik Beno
 */
public abstract class AbstractInstruction extends ChainImpl<Instruction> implements Instruction {

	int opcode;

	protected AbstractInstruction(int opcode) {
		this.opcode = opcode;
	}

	protected AbstractInstruction(int opcode, CodeParser codeParser) {
		this(opcode);
		codeParser.append(this);
	}

	public int opcode() {
		return opcode;
	}

	public int size() {
		return 1;
	}

	// todo optimize this (this is HUGE performance bottleneck)
	public int offset() {
		int offset = 0;
		Instruction insn = previous();
		while (insn != null) {
			offset += insn.size();
			insn = insn.previous();
		}
		return offset;
	}

	public List<Instruction> asList() {
		ArrayList<Instruction> list = new ArrayList<Instruction>(length());
		for (Instruction insn : this) {
			list.add(insn);
		}
		return list;
	}

	public void load(CodeParser codeParser) {
		// opcode we already have
		loadOperands(codeParser);
	}

	public void registerConstants(ConstantPool pool) {
	}

	public void save(XDataOutput out) {
		Log.debug("\t%s", this);
		out.writeByte(opcode);
		saveOperands(out);
	}

	protected abstract void loadOperands(CodeParser codeParser);

	protected abstract void saveOperands(XDataOutput out);

	public String toString() {
		return String.format("#%d (@%d) %s", index()+1, offset(), Opcode.forOpcode(opcode()));
	}
}
