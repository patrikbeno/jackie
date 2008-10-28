package org.jackie.jclassfile.code;

import static org.jackie.jclassfile.code.InstructionFactoryManager.instructionFactoryManager;
import org.jackie.jclassfile.constantpool.ConstantPool;
import static org.jackie.utils.Assert.expected;
import org.jackie.utils.Log;
import org.jackie.utils.XDataInput;

/**
 * @author Patrik Beno
 */
public class CodeParser {

	public Instruction parse(XDataInput in, int length, ConstantPool pool) {

		int toread = length;
		Instruction insn = null;

		while (toread > 0) {
			insn = read(in, insn, pool);
			toread -= insn.size();
		}

		expected(0, toread, "length != read");

		return insn.head();
	}

	Instruction read(XDataInput in, Instruction previous, ConstantPool pool) {
		int opcode = in.readUnsignedByte();

		InstructionFactory f = instructionFactoryManager().getFactory(opcode);
		Instruction insn = f.loadInstruction(opcode, previous, in, pool);

		Log.debug("Loaded instruction :%s", insn);

		return insn;
	}

}
