package org.jackie.jclassfile.code.impl;

import static org.jackie.jclassfile.ClassFileContext.classFileContext;
import org.jackie.jclassfile.code.Instruction;
import org.jackie.jclassfile.code.InstructionFactory;
import org.jackie.jclassfile.code.impl.Instructions.ArrayInstruction;
import org.jackie.jclassfile.code.impl.Instructions.BranchOffsetInstruction;
import org.jackie.jclassfile.code.impl.Instructions.FrameRefInstruction;
import org.jackie.jclassfile.code.impl.Instructions.IntegerInstruction;
import org.jackie.jclassfile.code.impl.Instructions.PoolRefInstruction;
import org.jackie.jclassfile.code.impl.Instructions.SimpleInstruction;
import org.jackie.jclassfile.code.impl.Instructions.ByteInstruction;
import org.jackie.jclassfile.code.impl.Instructions.ShortInstruction;
import org.jackie.jclassfile.code.impl.Instructions.LocalVarOpInstruction;
import org.jackie.jclassfile.code.impl.Instructions.BytePoolRefInstruction;
import org.jackie.jclassfile.code.impl.Instructions.SwitchInstruction;
import org.jackie.jclassfile.constantpool.Constant;
import org.jackie.utils.Assert;

import java.io.DataInput;
import java.io.IOException;

/**
 * @author Patrik Beno
 */
public class Factories {

	static public final InstructionFactory SIMPLE = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, DataInput in, Instruction previous) throws IOException {
			return new SimpleInstruction(opcode, in, previous);
		}
	};

	static public final InstructionFactory POOLREF = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, DataInput in, Instruction previous) throws IOException {
			return new PoolRefInstruction<Constant>(opcode, in, previous);
		}
	};

	static public final InstructionFactory FIELDREF = POOLREF;
	static public final InstructionFactory METHODREF = POOLREF;
	static public final InstructionFactory CLASSREF = POOLREF;

	static public final InstructionFactory POOLREF_BYTE = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, DataInput in, Instruction previous) throws IOException {
			return new BytePoolRefInstruction(opcode, in, previous);
		}
	};

	static public final InstructionFactory BRANCHOFFSET = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, DataInput in, Instruction previous) throws IOException {
			return new BranchOffsetInstruction(opcode, in, previous);
		}
	};

	static public final InstructionFactory FRAMEREF = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, DataInput in, Instruction previous) throws IOException {
			return new FrameRefInstruction(opcode, in, previous);
		}
	};

	static public final InstructionFactory BYTE = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, DataInput in, Instruction previous) throws IOException {
			return new ByteInstruction(opcode, in, previous);
		}
	};

	static public final InstructionFactory SHORT = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, DataInput in, Instruction previous) throws IOException {
			return new ShortInstruction(opcode, in, previous);
		}
	};

	static public final InstructionFactory INTEGER = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, DataInput in, Instruction previous) throws IOException {
			return new IntegerInstruction(opcode, in, previous);
		}
	};

	static public final InstructionFactory ARRAY = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, DataInput in, Instruction previous) throws IOException {
			return new ArrayInstruction(opcode, in, previous);
		}
	};

	static public final InstructionFactory LOCALVAROP = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, DataInput in, Instruction previous) throws IOException {
			return new LocalVarOpInstruction(opcode, in, previous);
		}
	};

	static public final InstructionFactory SWITCH = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, DataInput in, Instruction previous) throws IOException {
			return new SwitchInstruction(opcode, in, previous);
		}
	};

	///

	static public final InstructionFactory UNSUPPORTED = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, DataInput in, Instruction previous) throws IOException {
			throw Assert.unsupported("%s", Opcode.forOpcode(opcode));
		}
	};
}
