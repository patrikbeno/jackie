package org.jackie.jclassfile.code;

import static org.jackie.jclassfile.code.InstructionFactoryManager.instructionFactoryManager;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Patrik Beno
 */
public class CodeParser {

	public List<Instruction> parse(DataInput in, int length) throws IOException {

		List<Instruction> instructions = new ArrayList<Instruction>();

		int toread = length;
		while (toread > 0) {
			Instruction insn = read(in);
			instructions.add(insn);
			toread -= insn.size();
		}

		return instructions;
	}

	Instruction read(DataInput in) throws IOException {
		int opcode = in.readUnsignedByte();

		InstructionFactory f = instructionFactoryManager().getFactory(opcode);
		Instruction insn = f.loadInstruction(opcode, in);

		return insn;
	}

}
