package org.jackie.jclassfile.code.impl;

import org.jackie.jclassfile.code.Instruction;
import org.jackie.jclassfile.code.CodeParser;
import org.jackie.jclassfile.code.CodeResolver;
import org.jackie.jclassfile.constantpool.Constant;
import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.utils.Assert;
import org.jackie.utils.Countdown;
import org.jackie.utils.XDataOutput;
import org.jackie.utils.XDataInput;
import static org.jackie.utils.Assert.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Patrik Beno
 */
public class Instructions {

	static public class SimpleInstruction extends AbstractInstruction {
		public SimpleInstruction(int opcode, CodeParser codeParser) {
			super(opcode, codeParser);
		}

		public SimpleInstruction(int opcode) {
			super(opcode);
		}

		protected void loadOperands(CodeParser codeParser) {
		}
		protected void saveOperands(XDataOutput out) {
		}
	}

	static public class PoolRefInstruction<T extends Constant> extends AbstractInstruction {

		T constant;

		public PoolRefInstruction(int opcode, CodeParser codeParser) {
			super(opcode, codeParser);
		}

		public PoolRefInstruction(int opcode, T constant) {
			super(opcode);
			this.constant = constant;
		}

		protected void loadOperands(CodeParser codeParser) {
			int index = codeParser.input().readUnsignedShort();
			constant = (T) codeParser.constantPool().getConstant(index, Constant.class);
		}

		@Override
		public void registerConstants(ConstantPool pool) {
			super.registerConstants(pool);
			constant = pool.register(constant);
		}

		protected void saveOperands(XDataOutput out) {
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

		public BytePoolRefInstruction(int opcode, CodeParser codeParser) {
			super(opcode, codeParser);
		}

		public BytePoolRefInstruction(int opcode, Constant constant) {
			super(opcode, constant);
		}

		protected void loadOperands(CodeParser codeParser) {
			int index = codeParser.input().readUnsignedByte();
			constant = NOTNULL(codeParser.constantPool().getConstant(index, Constant.class));
		}

		@Override
		public void registerConstants(ConstantPool pool) {
			super.registerConstants(pool);
			pool.register(constant, true);
		}

		protected void saveOperands(XDataOutput out) {
			constant.writeByteReference(out);
			doAssert(constant.index() <= 255, "invalid index (%s>=255). Instruction: %s", constant.index(), this);
		}

		public int size() {
			return 2;
		}
	}

	static public class InvokeInterfaceInstruction extends PoolRefInstruction<Constant> {

		int nargs;

		public InvokeInterfaceInstruction(int opcode, CodeParser codeParser) {
			super(opcode, codeParser);
		}

		public InvokeInterfaceInstruction(int opcode, Constant constant) {
			super(opcode, constant);
		}

		@Override
		protected void loadOperands(CodeParser codeParser) {
			super.loadOperands(codeParser);
			XDataInput in = codeParser.input();
			nargs = in.readUnsignedByte();
			int end = in.readUnsignedByte(); // should be 0 todo: assert
		}

		@Override
		protected void saveOperands(XDataOutput out) {
			super.saveOperands(out);
			out.writeByte(nargs);
			out.writeByte(0);
		}

		@Override
		public int size() {
			return super.size() + 2;
		}
	}

	static public class BranchOffsetInstruction extends AbstractInstruction {

		Instruction instruction;

		public BranchOffsetInstruction(int opcode, CodeParser codeParser) {
			super(opcode, codeParser);
		}

		public BranchOffsetInstruction(int opcode, Instruction instruction) {
			super(opcode);
			this.instruction = instruction;
		}

		protected void loadOperands(CodeParser codeParser) {
			final int branchoffset = codeParser.input().readShort();
			codeParser.addResolver(new CodeResolver() {
				public void execute() {
					// resolve branchoffset to instruction
					int absoffset = offset() + branchoffset;
					Instruction insn = head();
					int offset = 0;
					while (offset < absoffset) {
						offset += insn.size();
						insn = insn.next();
					}

					expected(absoffset, offset, "Offset not found");

					instruction = insn;
				}
			});
		}

		@Override
		public void registerConstants(ConstantPool pool) {
			super.registerConstants(pool);
			instruction.registerConstants(pool);
		}

		protected void saveOperands(XDataOutput out) {
			int branchoffset = instruction().offset() - offset();
			out.writeShort(branchoffset);
		}

		public int size() {
			return 3;
		}

		public Instruction instruction() {
			return instruction;
		}

		@Override
		public String toString() {
			return String.format("%s #%s (@%s)", super.toString(), instruction().index(), instruction().offset());
		}
	}

	static public class FrameRefInstruction extends AbstractInstruction {

		int index; // local variable index

		public FrameRefInstruction(int opcode, CodeParser codeParser) {
			super(opcode, codeParser);
		}


		public FrameRefInstruction(int opcode, int index) {
			super(opcode);
			this.index = index;
		}

		protected void loadOperands(CodeParser codeParser) {
			index = codeParser.input().readUnsignedByte();
		}

		protected void saveOperands(XDataOutput out) {
			out.writeByte(index);
		}

		public int size() {
			return 2;
		}

	}

	static abstract public class ConstantInstruction<T> extends AbstractInstruction {

		T value;

		public ConstantInstruction(int opcode, CodeParser codeParser) {
			super(opcode, codeParser);
		}


		protected ConstantInstruction(int opcode, T value) {
			super(opcode);
			this.value = value;
		}
	}

	static public class ByteInstruction extends ConstantInstruction<Byte> {

		public ByteInstruction(int opcode, CodeParser codeParser) {
			super(opcode, codeParser);
		}

		public ByteInstruction(int opcode, Byte value) {
			super(opcode, value);
		}

		protected void loadOperands(CodeParser codeParser) {
			value = codeParser.input().readByte();
		}

		protected void saveOperands(XDataOutput out) {
			out.writeByte(value);
		}

		public int size() {
			return 2;
		}
	}

	static public class ShortInstruction extends ConstantInstruction<Short> {
		public ShortInstruction(int opcode, CodeParser codeParser) {
			super(opcode, codeParser);
		}

		public ShortInstruction(int opcode, Short value) {
			super(opcode, value);
		}

		protected void loadOperands(CodeParser codeParser) {
			value = codeParser.input().readShort();
		}

		protected void saveOperands(XDataOutput out) {
			out.writeShort(value);
		}

		public int size() {
			return super.size() + 2;
		}
	}

	static public class IntegerInstruction extends ConstantInstruction<Integer> {
		public IntegerInstruction(int opcode, CodeParser codeParser) {
			super(opcode, codeParser);
		}

		public IntegerInstruction(int opcode, Integer value) {
			super(opcode, value);
		}

		protected void loadOperands(CodeParser codeParser) {
			value = codeParser.input().readInt();
		}

		protected void saveOperands(XDataOutput out) {
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

		public ArrayInstruction(int opcode, CodeParser codeParser) {
			super(opcode, codeParser);
		}

		public ArrayInstruction(int opcode, Class type) {
			super(opcode);
			this.type = Type.forClass(type);
		}

		protected void loadOperands(CodeParser codeParser) {
			type = Type.forCode(codeParser.input().readUnsignedByte());
		}

		protected void saveOperands(XDataOutput out) {
			out.writeByte(type.code);
		}

		public int size() {
			return 2;
		}

	}

	static public class LocalVarOpInstruction extends AbstractInstruction {

		int index;
		int value;

		public LocalVarOpInstruction(int opcode, CodeParser codeParser) {
			super(opcode, codeParser);
		}

		public LocalVarOpInstruction(int opcode, int index, int value) {
			super(opcode);
			this.index = index;
			this.value = value;
		}

		protected void loadOperands(CodeParser codeParser) {
			XDataInput in = codeParser.input();
			index = in.readUnsignedByte();
			value = in.readUnsignedByte();
		}

		protected void saveOperands(XDataOutput out) {
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

		public LookupSwitchInstruction(int opcode, CodeParser codeParser) {
			super(opcode, codeParser);
		}

		protected void loadOperands(CodeParser codeParser) {
			XDataInput in = codeParser.input();

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

		protected void saveOperands(XDataOutput out) {
			// padding
			for (Countdown c = new Countdown(padding()); c.next();) {
				out.writeByte(0);
			}
			out.writeInt(dflt);
			out.writeInt(matches.size());
			for (Match m : matches) {
				out.writeInt(m.match);
				out.writeInt(m.offset);
			}
		}

		int padding() {
			return 3 - offset() % 4;
		}

		public int size() {
			return 1 /*opcode*/ + padding() + 4 /*dflt*/ + 4 /*npairs*/ + matches.size()*8;
		}
	}

	static public class TableSwitchInstruction extends AbstractInstruction {

		int dflt;
		int low;
		int high;
		int[] jumpoffsets;

		public TableSwitchInstruction(int opcode, CodeParser codeParser) {
			super(opcode, codeParser);
		}

		protected void loadOperands(CodeParser codeParser) {
			XDataInput in = codeParser.input();

			for (Countdown c = new Countdown(padding()); c.next();) {
				in.readByte();
			}

			dflt = in.readInt();
			low = in.readInt();
			high = in.readInt();
			doAssert(low <= high, "low(%d)<=high(%d)", low, high);

			int count = high - low + 1;
			doAssert(count<0xFFFF, "suspicious count: %s", count);

			jumpoffsets = new int[count];
			for (int i=0; i<jumpoffsets.length; i++) {
				jumpoffsets[i] = in.readInt();
			}
		}

		protected void saveOperands(XDataOutput out) {
			// padding
			for (Countdown c = new Countdown(padding()); c.next();) {
				out.writeByte(0);
			}
			out.writeInt(dflt);
			out.writeInt(low);
			out.writeInt(high);

			for (int offset : jumpoffsets) {
				out.writeInt(offset);
			}
		}

		int padding() {
			return 3 - offset() % 4;
		}

		public int size() {
			return 1 /*opcode*/ + padding() + 4*3 /*dflt,low,high*/ + jumpoffsets.length*4;
		}

		@Override
		public String toString() {
			return String.format("%s (low:%s, high:%s, dflt:%s, jumpoffsets: %s)", super.toString(), low, high, dflt, Arrays.toString(jumpoffsets));
		}
	}

	static public class MultiArrayInstruction extends PoolRefInstruction {

		int dimensions;

		public MultiArrayInstruction(int opcode, CodeParser codeParser) {
			super(opcode, codeParser);
		}


		protected void loadOperands(CodeParser codeParser) {
			super.loadOperands(codeParser);
			dimensions = codeParser.input().readUnsignedByte();
		}

		protected void saveOperands(XDataOutput out) {
			super.saveOperands(out);
			out.writeByte(dimensions);
		}

		public int size() {
			return super.size() + 1;
		}
	}






}
