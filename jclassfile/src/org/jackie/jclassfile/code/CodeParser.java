package org.jackie.jclassfile.code;

import static org.jackie.jclassfile.code.InstructionFactoryManager.instructionFactoryManager;
import static org.jackie.utils.Assert.expected;
import org.jackie.utils.Log;

import java.io.IOException;
import java.io.DataInput;

/**
 * @author Patrik Beno
 */
public class CodeParser {

	public Instruction parse(DataInput in, int length) throws IOException {

		int toread = length;
		Instruction insn = null;

		while (toread > 0) {
			insn = read(in, insn);
			toread -= insn.size();
		}

		expected(0, toread, "length != read");

		return insn.head();
	}

	Instruction read(DataInput in, Instruction previous) throws IOException {
		int opcode = in.readUnsignedByte();

		InstructionFactory f = instructionFactoryManager().getFactory(opcode);
		Instruction insn = f.loadInstruction(opcode, in, previous);

		Log.debug("Loaded instruction :%s", insn);

		return insn;
	}

}
