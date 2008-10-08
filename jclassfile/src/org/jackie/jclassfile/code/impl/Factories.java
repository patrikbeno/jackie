package org.jackie.jclassfile.code.impl;

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
import org.jackie.jclassfile.code.impl.Instructions.LookupSwitchInstruction;
import org.jackie.jclassfile.code.impl.Instructions.TableSwitchInstruction;
import org.jackie.jclassfile.code.impl.Instructions.MultiArrayInstruction;
import org.jackie.jclassfile.constantpool.Constant;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.utils.Assert;

import java.io.DataInput;
import java.io.IOException;

/**
 * @author Patrik Beno
 */
public class Factories {

	static public final InstructionFactory SIMPLE = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, Instruction previous, DataInput in,
													  ConstantPool pool) throws IOException {
			return load(in, pool, new SimpleInstruction(opcode, previous));
		}
	};

	static public final InstructionFactory POOLREF = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, Instruction previous, DataInput in,
													  ConstantPool pool) throws IOException {
			return load(in, pool, new PoolRefInstruction<Constant>(opcode, previous));
		}
	};

	static public final InstructionFactory FIELDREF = POOLREF;
	static public final InstructionFactory METHODREF = POOLREF;
	static public final InstructionFactory CLASSREF = POOLREF;

	static public final InstructionFactory POOLREF_BYTE = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, Instruction previous, DataInput in,
													  ConstantPool pool) throws IOException {
			return load(in, pool, new BytePoolRefInstruction(opcode, previous));
		}
	};

	static public final InstructionFactory BRANCHOFFSET = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, Instruction previous, DataInput in,
													  ConstantPool pool) throws IOException {
			return load(in, pool, new BranchOffsetInstruction(opcode, previous));
		}
	};

	static public final InstructionFactory FRAMEREF = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, Instruction previous, DataInput in,
													  ConstantPool pool) throws IOException {
			return load(in, pool, new FrameRefInstruction(opcode, previous));
		}
	};

	static public final InstructionFactory BYTE = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, Instruction previous, DataInput in,
													  ConstantPool pool) throws IOException {
			return load(in, pool, new ByteInstruction(opcode, previous));
		}
	};

	static public final InstructionFactory SHORT = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, Instruction previous, DataInput in,
													  ConstantPool pool) throws IOException {
			return load(in, pool, new ShortInstruction(opcode, previous));
		}
	};

	static public final InstructionFactory INTEGER = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, Instruction previous, DataInput in,
													  ConstantPool pool) throws IOException {
			return new IntegerInstruction(opcode, previous);
		}
	};

	static public final InstructionFactory ARRAY = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, Instruction previous, DataInput in,
													  ConstantPool pool) throws IOException {
			return load(in, pool, new ArrayInstruction(opcode, previous));
		}
	};

	static public final InstructionFactory LOCALVAROP = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, Instruction previous, DataInput in,
													  ConstantPool pool) throws IOException {
			return load(in, pool, new LocalVarOpInstruction(opcode, previous));
		}
	};

	static public final InstructionFactory LOOKUPSWITCH = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, Instruction previous, DataInput in,
													  ConstantPool pool) throws IOException {
			return load(in, pool, new LookupSwitchInstruction(opcode, previous));
		}
	};

	static public final InstructionFactory SWITCHTABLE = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, Instruction previous, DataInput in,
													  ConstantPool pool) throws IOException {
			return load(in, pool, new TableSwitchInstruction(opcode, previous));
		}
	};

	static public final InstructionFactory MULTIARRAY = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, Instruction previous, DataInput in,
													  ConstantPool pool) throws IOException {
			return load(in, pool, new MultiArrayInstruction(opcode, previous));
		}
	};

	///

	static public final InstructionFactory UNSUPPORTED = new InstructionFactory() {
		public Instruction loadInstruction(int opcode, Instruction previous, DataInput in,
													  ConstantPool pool) throws IOException {
			throw Assert.unsupported("%s", Opcode.forOpcode(opcode));
		}
	};

	static Instruction load(DataInput in, ConstantPool pool, AbstractInstruction insn) throws IOException {
		insn.load(in, pool);
		return insn;
	}
}
