package org.jackie.jclassfile.code;

import static org.jackie.jclassfile.code.InstructionFactoryManager.instructionFactoryManager;

import java.io.IOException;
import java.io.DataInput;
import java.io.DataOutput;
import java.util.BitSet;

/**
 * @author Patrik Beno
 */
public class SimpleInstructionFactory implements InstructionFactory {

	static class SimpleInstruction implements Instruction {

		int opcode;

		SimpleInstruction(int opcode) {
			this.opcode = opcode;
		}

		public int opcode() {
			return opcode;
		}

		public int size() {
			return 1;
		}

		public void save(DataOutput out) throws IOException {
			out.writeByte(opcode); 
		}

		public String toString() {
			return String.format("%s", Opcode.forOpcode(opcode));
		}
	}

	static BitSet supported = new BitSet(0xFF);

	static {
		for (Opcode o : Opcode.values()) {
			if (o.operands().isEmpty()) {
				supported.set(o.opcode());
			}
		}
	}

	{
		instructionFactoryManager().registerFactory(supported, this);
	}

	public Instruction loadInstruction(int opcode, DataInput in) throws IOException {
		assert supported.get(opcode) : String.format("Unsupported opcode: %s", opcode);
		return new SimpleInstruction(opcode);
	}
}
