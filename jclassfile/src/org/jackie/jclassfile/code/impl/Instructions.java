package org.jackie.jclassfile.code.impl;

import org.jackie.jclassfile.code.Instruction;
import org.jackie.jclassfile.constantpool.Constant;
import static org.jackie.jclassfile.ClassFileContext.classFileContext;
import static org.jackie.utils.Assert.expected;
import static org.jackie.utils.Assert.doAssert;
import org.jackie.utils.Assert;
import org.jackie.utils.Countdown;

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

		public SimpleInstruction(int opcode) {
			super(opcode);
		}

		protected void loadOperands(DataInput in) throws IOException {
		}
		protected void saveOperands(DataOutput out) throws IOException {
		}
	}

	static public class PoolRefInstruction<T extends Constant> extends AbstractInstruction {

		T constant;

		public PoolRefInstruction(int opcode, DataInput in, Instruction previous) throws IOException {
			super(opcode, in, previous);
		}

		public PoolRefInstruction(int opcode, T constant) {
			super(opcode);
			this.constant = constant;
		}

		protected void loadOperands(DataInput in) throws IOException {
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

		public BytePoolRefInstruction(int opcode, Constant constant) {
			super(opcode, constant);
		}

		protected void loadOperands(DataInput in) throws IOException {
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

		public BranchOffsetInstruction(int opcode, Instruction instruction) {
			super(opcode);
			this.instruction = instruction;
		}

		protected void loadOperands(DataInput in) throws IOException {
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

		public FrameRefInstruction(int opcode, int index) {
			super(opcode);
			this.index = index;
		}

		protected void loadOperands(DataInput in) throws IOException {
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

		protected ConstantInstruction(int opcode, T value) {
			super(opcode);
			this.value = value;
		}
	}

	static public class ByteInstruction extends ConstantInstruction<Byte> {
		public ByteInstruction(int opcode, DataInput in, Instruction previous) throws IOException {
			super(opcode, in, previous);
		}

		public ByteInstruction(int opcode, Byte value) {
			super(opcode, value);
		}

		protected void loadOperands(DataInput in) throws IOException {
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

		public ShortInstruction(int opcode, Short value) {
			super(opcode, value);
		}

		protected void loadOperands(DataInput in) throws IOException {
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

		public IntegerInstruction(int opcode, Integer value) {
			super(opcode, value);
		}

		protected void loadOperands(DataInput in) throws IOException {
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

		static public enum Type {
			BOOLEAN(4, boolean.class),
			CHAR(5, char.class),
			FLOAT(6, float.class),
			DOUBLE(7, double.class),
			BYTE(8, byte.class),
			SHORT(9, short.class),
			INT(10, int.class),
			LONG(11, long.class),
			;
			int code;
			Class type;

			Type(int code, Class type) {
				this.code = code;
				this.type = type;
			}

			static Type forClass(Class cls) {
				for (Type t : values()) {
					if (t.type.equals(cls)) {
						return t;
					}
				}
				throw Assert.invariantFailed("No type for %s", cls);
			}

			static Type forCode(int code) {
				for (Type t : values()) {
					if (t.code == code) {
						return t;
					}
				}
				throw Assert.invariantFailed("No type for code: %d", code);
			}
		}

		Type type;

		public ArrayInstruction(int opcode, DataInput in, Instruction previous) throws IOException {
			super(opcode, in, previous);
		}

		public ArrayInstruction(int opcode, Class type) {
			super(opcode);
			this.type = Type.forClass(type);
		}

		protected void loadOperands(DataInput in) throws IOException {
			type = Type.forCode(in.readUnsignedByte());
		}

		protected void saveOperands(DataOutput out) throws IOException {
			out.writeByte(type.code);
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

		public LocalVarOpInstruction(int opcode, int index, int value) {
			super(opcode);
			this.index = index;
			this.value = value;
		}

		protected void loadOperands(DataInput in) throws IOException {
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

		public String toString() {
			return String.format("%d:%d", match, offset);
		}
	}

	static public class LookupSwitchInstruction extends AbstractInstruction {

		int dflt;
		List<Match> matches;

		public LookupSwitchInstruction(int opcode, DataInput in, Instruction previous) throws IOException {
			super(opcode, in, previous);
		}

		protected void loadOperands(DataInput in) throws IOException {
			for (Countdown c = new Countdown(padding()); c.next();) {
				in.readByte();
			}

			dflt = in.readInt();
			int npairs = in.readInt();
			matches = new ArrayList<Match>(npairs);
			for (Countdown c = new Countdown(npairs); c.next();) {
				Match m = new Match(in.readInt(), in.readInt());
				matches.add(m);
			}
		}

		protected void saveOperands(DataOutput out) throws IOException {
			throw Assert.notYetImplemented(); // todo implement LookupSwitchInstruction.saveOperands()
		}

		int padding() {
			return 3 - offset() % 4;
		}

		public int size() {
			return 1 /*opcode*/ + padding() + 4 /*dflt*/ + 4 /*npairs*/ + matches.size()*8;
		}
	}

	static public class SwitchTableInstruction extends AbstractInstruction {

		int dflt;
		int low;
		int high;
		List<Match> matches;

		public SwitchTableInstruction(int opcode, DataInput in, Instruction previous) throws IOException {
			super(opcode, in, previous);
		}

		protected void loadOperands(DataInput in) throws IOException {
			for (Countdown c = new Countdown(padding()); c.next();) {
				in.readByte();
			}

			dflt = in.readInt();
			low = in.readInt();
			high = in.readInt();
			doAssert(low <= high, "low(%d)<=high(%d)", low, high);

			int count = high - low + 1;
			matches = new ArrayList<Match>(count);
			for (Countdown c = new Countdown(count); c.next();) {
				Match m = new Match(in.readInt(), in.readInt());
				matches.add(m);
			}
		}

		protected void saveOperands(DataOutput out) throws IOException {
			throw Assert.notYetImplemented(); // todo implement LookupSwitchInstruction.saveOperands()
		}

		int padding() {
			return 3 - offset() % 4;
		}

		public int size() {
			return 1 /*opcode*/ + padding() + 4*3 /*dflt,low,high*/ + matches.size()*8;
		}
	}






}
