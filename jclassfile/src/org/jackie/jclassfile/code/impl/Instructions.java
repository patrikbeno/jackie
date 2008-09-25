package org.jackie.jclassfile.code.impl;

import org.jackie.jclassfile.code.Instruction;
import org.jackie.jclassfile.constantpool.Constant;
import static org.jackie.jclassfile.ClassFileContext.classFileContext;
import static org.jackie.utils.Assert.expected;
import org.jackie.utils.Assert;

import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Patrik Beno
 */
public class Instructions {

	static public class SimpleInstruction extends AbstractInstruction {
		public SimpleInstruction(int opcode, DataInput in, Instruction previous) throws IOException {
			super(opcode, in, previous);
		}

		protected void loadOperands(DataInput in, Instruction previous) throws IOException {
		}
		protected void saveOperands(DataOutput out) throws IOException {
		}
	}

	static public class PoolRefInstruction<T extends Constant> extends AbstractInstruction {

		T constant;

		public PoolRefInstruction(int opcode, DataInput in, Instruction previous) throws IOException {
			super(opcode, in, previous);
		}

		protected void loadOperands(DataInput in, Instruction previous) throws IOException {
			int index = in.readUnsignedShort();
			constant = (T) classFileContext().constantPool().getConstant(index, Constant.class);
		}

		protected void saveOperands(DataOutput out) throws IOException {
			constant.writeReference(out);
		}

		public int size() {
			return 3;
		}

		public String toString() {
			return String.format("%s %s", super.toString(), constant);
		}
	}

	static public class BytePoolRefInstruction extends PoolRefInstruction<Constant> {
		public BytePoolRefInstruction(int opcode, DataInput in, Instruction previous) throws IOException {
			super(opcode, in, previous);
		}

		protected void loadOperands(DataInput in, Instruction previous) throws IOException {
			int index = in.readUnsignedByte();
			constant = classFileContext().constantPool().getConstant(index, Constant.class);
		}

		protected void saveOperands(DataOutput out) throws IOException {
			constant.writeByteReference(out);
		}

		public int size() {
			return 2;
		}
	}

	static public class BranchOffsetInstruction extends AbstractInstruction {

		Short branchoffset;
		Instruction instruction;

		public BranchOffsetInstruction(int opcode, DataInput in, Instruction previous) throws IOException {
			super(opcode, in, previous);
		}

		protected void loadOperands(DataInput in, Instruction previous) throws IOException {
			branchoffset = in.readShort();
		}

		protected void saveOperands(DataOutput out) throws IOException {
			int branchoffset = instruction().offset() - offset();
			out.writeShort(branchoffset);
		}

		public int size() {
			return 3;
		}

		public Instruction instruction() {
			if (instruction != null) { return instruction; }

			// resolve branchoffset to instruction
			int absoffset = offset() + branchoffset;
			Instruction insn = head();
			int offset = 0;
			while (offset < absoffset) {
				offset += insn.size();
				insn = insn.next();
			}

			expected(absoffset, offset, "Offset not found");

			branchoffset = null; // forget this, we will recompute it in on demand

			return (this.instruction = insn);
		}
	}

	static public class FrameRefInstruction extends AbstractInstruction {

		int index; // local variable index

		public FrameRefInstruction(int opcode, DataInput in, Instruction previous) throws IOException {
			super(opcode, in, previous);
		}

		protected void loadOperands(DataInput in, Instruction previous) throws IOException {
			index = in.readUnsignedByte();
		}

		protected void saveOperands(DataOutput out) throws IOException {
			out.writeByte(index);
		}

		public int size() {
			return 2;
		}

	}

	static abstract public class ConstantInstruction<T> extends AbstractInstruction {
		T value;

		protected ConstantInstruction(int opcode, DataInput in, Instruction previous) throws IOException {
			super(opcode, in, previous);
		}
	}

	static public class ByteInstruction extends ConstantInstruction<Byte> {
		public ByteInstruction(int opcode, DataInput in, Instruction previous) throws IOException {
			super(opcode, in, previous);
		}

		protected void loadOperands(DataInput in, Instruction previous) throws IOException {
			value = in.readByte();
		}

		protected void saveOperands(DataOutput out) throws IOException {
			out.writeByte(value);
		}
		public int size() {
			return 2;
		}
	}

	static public class ShortInstruction extends ConstantInstruction<Short> {
		public ShortInstruction(int opcode, DataInput in, Instruction previous) throws IOException {
			super(opcode, in, previous);
		}

		protected void loadOperands(DataInput in, Instruction previous) throws IOException {
			value = in.readShort();
		}

		protected void saveOperands(DataOutput out) throws IOException {
			out.writeShort(value);
		}
		public int size() {
			return super.size() + 2;
		}
	}

	static public class IntegerInstruction extends ConstantInstruction<Integer> {
		public IntegerInstruction(int opcode, DataInput in, Instruction previous) throws IOException {
			super(opcode, in, previous);
		}

		protected void loadOperands(DataInput in, Instruction previous) throws IOException {
			value = in.readInt();
		}

		protected void saveOperands(DataOutput out) throws IOException {
			out.writeInt(value);
		}
		public int size() {
			return 5;
		}
	}

	static public class ArrayInstruction extends AbstractInstruction {
		
		static final int T_BOOLEAN = 4;
		static final int T_CHAR 	= 5;
		static final int T_FLOAT 	= 6;
		static final int T_DOUBLE 	= 7;
		static final int T_BYTE 	= 8;
		static final int T_SHORT 	= 9;
		static final int T_INT 		= 10;
		static final int T_LONG 	= 11;

		int type;

		public ArrayInstruction(int opcode, DataInput in, Instruction previous) throws IOException {
			super(opcode, in, previous);
		}

		protected void loadOperands(DataInput in, Instruction previous) throws IOException {
			type = in.readUnsignedByte();
		}

		protected void saveOperands(DataOutput out) throws IOException {
			out.writeByte(type); 
		}
		public int size() {
			return 2;
		}

	}

	static public class LocalVarOpInstruction extends AbstractInstruction {

		int index;
		int value;

		public LocalVarOpInstruction(int opcode, DataInput in, Instruction previous) throws IOException {
			super(opcode, in, previous);
		}

		protected void loadOperands(DataInput in, Instruction previous) throws IOException {
			index = in.readUnsignedByte();
			value = in.readUnsignedByte();
		}

		protected void saveOperands(DataOutput out) throws IOException {
			out.writeByte(index);
			out.writeByte(value);
		}

		public int size() {
			return 3;
		}
	}

	static public class Match {
		public int match;
		public int offset;

		public Match(int match, int offset) {
			this.match = match;
			this.offset = offset;
		}
	}

	static public class SwitchInstruction extends AbstractInstruction {

		int dflt;
		List<Match> matches;

		public SwitchInstruction(int opcode, DataInput in, Instruction previous) throws IOException {
			super(opcode, in, previous);
		}

		protected void loadOperands(DataInput in, Instruction previous) throws IOException {
			int offset = (previous != null) ? previous.offset() + previous.size() : 0;
			int padding = offset % 4 - 1;
			while (padding-- > 0) { in.readByte(); }

			dflt = in.readInt();
			int npairs = in.readInt();
			matches = new ArrayList<Match>(npairs);
			while (npairs-- > 0) {
				Match c = new Match(in.readInt(), in.readInt());
				matches.add(c);
			}
		}

		protected void saveOperands(DataOutput out) throws IOException {
			throw Assert.notYetImplemented(); // todo implement SwitchInstruction.saveOperands()
		}

		public int size() {
			int padding = offset() % 4 - 1;
			return 1 /*opcode*/ + padding + 4 /*dflt*/ + 4 /*npairs*/ + matches.size()*8;
		}
	}

	




}
