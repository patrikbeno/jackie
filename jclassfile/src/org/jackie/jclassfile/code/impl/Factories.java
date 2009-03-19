package org.jackie.jclassfile.code.impl;

import org.jackie.jclassfile.code.Instruction;
import org.jackie.jclassfile.code.InstructionFactory;
import org.jackie.jclassfile.code.CodeParser;
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
import org.jackie.jclassfile.code.impl.Instructions.LookupSwitchInstruction;
import org.jackie.jclassfile.code.impl.Instructions.TableSwitchInstruction;
import org.jackie.jclassfile.code.impl.Instructions.MultiArrayInstruction;
import org.jackie.jclassfile.code.impl.Instructions.InvokeInterfaceInstruction;
import org.jackie.jclassfile.constantpool.Constant;
import org.jackie.utils.Assert;

/**
 * @author Patrik Beno
 */
public class Factories {

	static public final InstructionFactory SIMPLE = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, CodeParser codeParser) {
			return load(codeParser, new SimpleInstruction(opcode, codeParser));
		}
	};

	static public final InstructionFactory POOLREF = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, CodeParser codeParser) {
			return load(codeParser, new PoolRefInstruction<Constant>(opcode, codeParser));
		}
	};

	static public final InstructionFactory FIELDREF = POOLREF;
	static public final InstructionFactory METHODREF = POOLREF;
	static public final InstructionFactory CLASSREF = POOLREF;

	static public final InstructionFactory POOLREF_BYTE = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, CodeParser codeParser) {
			return load(codeParser, new BytePoolRefInstruction(opcode, codeParser));
		}
	};

	static public final InstructionFactory INVOKE_INTERFACE = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, CodeParser codeParser) {
			return load(codeParser, new InvokeInterfaceInstruction(opcode, codeParser));
		}
	};

	static public final InstructionFactory BRANCHOFFSET = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, CodeParser codeParser) {
			return load(codeParser, new BranchOffsetInstruction(opcode, codeParser));
		}
	};

	static public final InstructionFactory FRAMEREF = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, CodeParser codeParser) {
			return load(codeParser, new FrameRefInstruction(opcode, codeParser));
		}
	};

	static public final InstructionFactory BYTE = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, CodeParser codeParser) {
			return load(codeParser, new ByteInstruction(opcode, codeParser));
		}
	};

	static public final InstructionFactory SHORT = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, CodeParser codeParser) {
			return load(codeParser, new ShortInstruction(opcode, codeParser));
		}
	};

	static public final InstructionFactory INTEGER = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, CodeParser codeParser) {
			return new IntegerInstruction(opcode, codeParser);
		}
	};

	static public final InstructionFactory ARRAY = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, CodeParser codeParser) {
			return load(codeParser, new ArrayInstruction(opcode, codeParser));
		}
	};

	static public final InstructionFactory LOCALVAROP = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, CodeParser codeParser) {
			return load(codeParser, new LocalVarOpInstruction(opcode, codeParser));
		}
	};

	static public final InstructionFactory LOOKUPSWITCH = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, CodeParser codeParser) {
			return load(codeParser, new LookupSwitchInstruction(opcode, codeParser));
		}
	};

	static public final InstructionFactory SWITCHTABLE = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, CodeParser codeParser) {
			return load(codeParser, new TableSwitchInstruction(opcode, codeParser));
		}
	};

	static public final InstructionFactory MULTIARRAY = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, CodeParser codeParser) {
			return load(codeParser, new MultiArrayInstruction(opcode, codeParser));
		}
	};

	///

	static public final InstructionFactory UNSUPPORTED = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, CodeParser codeParser) {
			throw Assert.unsupported("%s", Opcode.forOpcode(opcode));
		}
	};

	static Instruction load(CodeParser codeParser, AbstractInstruction insn) {
		insn.load(codeParser);
		return insn;
	}
}
