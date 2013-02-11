package org.jackie.jclassfile.code.impl;

import org.jackie.jclassfile.code.Instruction;
import org.jackie.jclassfile.code.impl.Instructions.SimpleInstruction;
import org.jackie.jclassfile.code.impl.Instructions.ByteInstruction;
import org.jackie.jclassfile.code.impl.Instructions.PoolRefInstruction;
import org.jackie.jclassfile.code.impl.Instructions.BytePoolRefInstruction;
import org.jackie.jclassfile.code.impl.Instructions.BranchOffsetInstruction;
import org.jackie.jclassfile.code.impl.Instructions.FrameRefInstruction;
import org.jackie.jclassfile.code.impl.Instructions.ShortInstruction;
import org.jackie.jclassfile.code.impl.Instructions.IntegerInstruction;
import org.jackie.jclassfile.code.impl.Instructions.ArrayInstruction;
import org.jackie.jclassfile.code.impl.Instructions.LocalVarOpInstruction;
import org.jackie.jclassfile.constantpool.Constant;
import org.jackie.utils.Assert;

/**
 * @author Patrik Beno
 */
public class InsnBuilder {

	Instruction chain;

	public InsnBuilder(Instruction chain) {
		this.chain = chain;
	}

	public InsnBuilder() {
	}

	private void append(Instruction insn) {
		chain = (chain != null) ? chain.append(insn) : insn;
	}

	public Instruction get() {
		return chain;
	}

	/// support

	/**
	 * saves link to the most recent instruction in the given label.
	 * Typical usage:
	 *
	 * <code><pre>
	 * Label jumphere = new Label();
	 * InsnBuilder builder = new InsnBuilder()
	 *     .nop()<b>.label(jumphere)</b> // save pointer here
	 *     .getstatic("fieldname")
	 *     .invokestatic("java/lang/System", "println")
	 *     .goto(<b>label.instruction()</b>); // use in here
	 * </pre></code>
	 *
	 * todo sample code will need review
	 *
	 * @param label
	 * @return
	 */
	public InsnBuilder label(Label label) {
		label.link(chain);
		return this;
	}

	/// generic


	protected InsnBuilder _unsupported(Opcode opcode) {
		throw Assert.unsupported("%s", opcode);
	}

	protected InsnBuilder _simple(Opcode opcode) {
		append(new SimpleInstruction(opcode.opcode()));
		return this;
	}

	protected <T extends Constant> InsnBuilder _poolref(Opcode opcode, T constant) {
		append(new PoolRefInstruction<T>(opcode.opcode(), constant));
		return this;
	}

	protected <T extends Constant> InsnBuilder _bytepoolref(Opcode opcode, T constant) {
		append(new BytePoolRefInstruction(opcode.opcode(), constant));
		return this;
	}

	protected InsnBuilder _branchoffset(Opcode opcode, Instruction insn) {
		append(new BranchOffsetInstruction(opcode.opcode(), insn));
		return this;
	}

	protected InsnBuilder _frameref(Opcode opcode, int index) {
		append(new FrameRefInstruction(opcode.opcode(), index));
		return this;
	}

	protected InsnBuilder _byte(Opcode opcode, byte value) {
		append(new ByteInstruction(opcode.opcode(), value));
		return this;
	}

	protected InsnBuilder _short(Opcode opcode, short value) {
		append(new ShortInstruction(opcode.opcode(), value));
		return this;
	}

	protected InsnBuilder _integer(Opcode opcode, int value) {
		append(new IntegerInstruction(opcode.opcode(), value));
		return this;
	}

	protected InsnBuilder _array(Opcode opcode, Class type) {
		append(new ArrayInstruction(opcode.opcode(), type));
		return this;
	}

	protected InsnBuilder _localvarop(Opcode opcode, int index, int value) {
		append(new LocalVarOpInstruction(opcode.opcode(), index, value));
		return this;
	}

	

	/// opcodes

	public InsnBuilder nop() { return _unsupported(Opcode.NOP); }

	public InsnBuilder aconst_null() { return _unsupported(Opcode.ACONST_NULL); }

	public InsnBuilder iconst_m1() { return _unsupported(Opcode.ICONST_M1); }
	public InsnBuilder iconst_0() { return _unsupported(Opcode.ICONST_0); }
	public InsnBuilder iconst_1() { return _unsupported(Opcode.ICONST_1); }
	public InsnBuilder iconst_2() { return _unsupported(Opcode.ICONST_2); }
	public InsnBuilder iconst_3() { return _unsupported(Opcode.ICONST_3); }
	public InsnBuilder iconst_4() { return _unsupported(Opcode.ICONST_4); }
	public InsnBuilder iconst_5() { return _unsupported(Opcode.ICONST_5); }

	public InsnBuilder lconst_0() { return _unsupported(Opcode.LCONST_0); }
	public InsnBuilder lconst_1() { return _unsupported(Opcode.LCONST_1); }

	public InsnBuilder fconst_0() { return _unsupported(Opcode.FCONST_0); }
	public InsnBuilder fconst_1() { return _unsupported(Opcode.FCONST_1); }
	public InsnBuilder fconst_2() { return _unsupported(Opcode.FCONST_2); }

	public InsnBuilder dconst_0() { return _unsupported(Opcode.DCONST_0); }
	public InsnBuilder dconst_1() { return _unsupported(Opcode.DCONST_1); }

	public InsnBuilder bipush() { return _unsupported(Opcode.BIPUSH); }
	public InsnBuilder sipush() { return _unsupported(Opcode.SIPUSH); }

	public InsnBuilder ldc() { return _unsupported(Opcode.LDC); }
	public InsnBuilder ldc_w() { return _unsupported(Opcode.LDC_W); }
	public InsnBuilder ldc2_w() { return _unsupported(Opcode.LDC2_W); }

	public InsnBuilder iload() { return _unsupported(Opcode.ILOAD); }
	public InsnBuilder lload() { return _unsupported(Opcode.LLOAD); }
	public InsnBuilder fload() { return _unsupported(Opcode.FLOAD); }
	public InsnBuilder dload() { return _unsupported(Opcode.DLOAD); }
	public InsnBuilder aload() { return _unsupported(Opcode.ALOAD); }

	public InsnBuilder iload_0() { return _unsupported(Opcode.ILOAD_0); }
	public InsnBuilder iload_1() { return _unsupported(Opcode.ILOAD_1); }
	public InsnBuilder iload_2() { return _unsupported(Opcode.ILOAD_2); }
	public InsnBuilder iload_3() { return _unsupported(Opcode.ILOAD_3); }

	public InsnBuilder lload_0() { return _unsupported(Opcode.LLOAD_0); }
	public InsnBuilder lload_1() { return _unsupported(Opcode.LLOAD_1); }
	public InsnBuilder lload_2() { return _unsupported(Opcode.LLOAD_2); }
	public InsnBuilder lload_3() { return _unsupported(Opcode.LLOAD_3); }

	public InsnBuilder fload_0() { return _unsupported(Opcode.FLOAD_0); }
	public InsnBuilder fload_1() { return _unsupported(Opcode.FLOAD_1); }
	public InsnBuilder fload_2() { return _unsupported(Opcode.FLOAD_2); }
	public InsnBuilder fload_3() { return _unsupported(Opcode.FLOAD_3); }

	public InsnBuilder dload_0() { return _unsupported(Opcode.DLOAD_0); }
	public InsnBuilder dload_1() { return _unsupported(Opcode.DLOAD_1); }
	public InsnBuilder dload_2() { return _unsupported(Opcode.DLOAD_2); }
	public InsnBuilder dload_3() { return _unsupported(Opcode.DLOAD_3); }

	public InsnBuilder aload_0() { return _unsupported(Opcode.ALOAD_0); }
	public InsnBuilder aload_1() { return _unsupported(Opcode.ALOAD_1); }
	public InsnBuilder aload_2() { return _unsupported(Opcode.ALOAD_2); }
	public InsnBuilder aload_3() { return _unsupported(Opcode.ALOAD_3); }

	public InsnBuilder iaload() { return _unsupported(Opcode.IALOAD); }
	public InsnBuilder laload() { return _unsupported(Opcode.LALOAD); }
	public InsnBuilder faload() { return _unsupported(Opcode.FALOAD); }
	public InsnBuilder daload() { return _unsupported(Opcode.DALOAD); }
	public InsnBuilder aaload() { return _unsupported(Opcode.AALOAD); }
	public InsnBuilder baload() { return _unsupported(Opcode.BALOAD); }
	public InsnBuilder caload() { return _unsupported(Opcode.CALOAD); }
	public InsnBuilder saload() { return _unsupported(Opcode.SALOAD); }

	public InsnBuilder istore() { return _unsupported(Opcode.ISTORE); }
	public InsnBuilder lstore() { return _unsupported(Opcode.LSTORE); }
	public InsnBuilder fstore() { return _unsupported(Opcode.FSTORE); }
	public InsnBuilder dstore() { return _unsupported(Opcode.DSTORE); }
	public InsnBuilder astore() { return _unsupported(Opcode.ASTORE); }

	public InsnBuilder istore_0() { return _unsupported(Opcode.ISTORE_0); }
	public InsnBuilder istore_1() { return _unsupported(Opcode.ISTORE_1); }
	public InsnBuilder istore_2() { return _unsupported(Opcode.ISTORE_2); }
	public InsnBuilder istore_3() { return _unsupported(Opcode.ISTORE_3); }

	public InsnBuilder lstore_0() { return _unsupported(Opcode.LSTORE_0); }
	public InsnBuilder lstore_1() { return _unsupported(Opcode.LSTORE_1); }
	public InsnBuilder lstore_2() { return _unsupported(Opcode.LSTORE_2); }
	public InsnBuilder lstore_3() { return _unsupported(Opcode.LSTORE_3); }

	public InsnBuilder fstore_0() { return _unsupported(Opcode.FSTORE_0); }
	public InsnBuilder fstore_1() { return _unsupported(Opcode.FSTORE_1); }
	public InsnBuilder fstore_2() { return _unsupported(Opcode.FSTORE_2); }
	public InsnBuilder fstore_3() { return _unsupported(Opcode.FSTORE_3); }

	public InsnBuilder dstore_0() { return _unsupported(Opcode.DSTORE_0); }
	public InsnBuilder dstore_1() { return _unsupported(Opcode.DSTORE_1); }
	public InsnBuilder dstore_2() { return _unsupported(Opcode.DSTORE_2); }
	public InsnBuilder dstore_3() { return _unsupported(Opcode.DSTORE_3); }

	public InsnBuilder astore_0() { return _unsupported(Opcode.ASTORE_0); }
	public InsnBuilder astore_1() { return _unsupported(Opcode.ASTORE_1); }
	public InsnBuilder astore_2() { return _unsupported(Opcode.ASTORE_2); }
	public InsnBuilder astore_3() { return _unsupported(Opcode.ASTORE_3); }

	public InsnBuilder iastore() { return _unsupported(Opcode.IASTORE); }
	public InsnBuilder lastore() { return _unsupported(Opcode.LASTORE); }
	public InsnBuilder fastore() { return _unsupported(Opcode.FASTORE); }
	public InsnBuilder dastore() { return _unsupported(Opcode.DASTORE); }
	public InsnBuilder aastore() { return _unsupported(Opcode.AASTORE); }
	public InsnBuilder bastore() { return _unsupported(Opcode.BASTORE); }
	public InsnBuilder castore() { return _unsupported(Opcode.CASTORE); }
	public InsnBuilder sastore() { return _unsupported(Opcode.SASTORE); }

	public InsnBuilder pop() { return _unsupported(Opcode.POP); }
	public InsnBuilder pop2() { return _unsupported(Opcode.POP2); }

	public InsnBuilder dup() { return _unsupported(Opcode.DUP); }
	public InsnBuilder dup_x1() { return _unsupported(Opcode.DUP_X1); }
	public InsnBuilder dup_x2() { return _unsupported(Opcode.DUP_X2); }

	public InsnBuilder dup2() { return _unsupported(Opcode.DUP2); }
	public InsnBuilder dup2_x1() { return _unsupported(Opcode.DUP2_X1); }
	public InsnBuilder dup2_x2() { return _unsupported(Opcode.DUP2_X2); }

	public InsnBuilder swap() { return _unsupported(Opcode.SWAP); }

	public InsnBuilder iadd() { return _unsupported(Opcode.IADD); }
	public InsnBuilder ladd() { return _unsupported(Opcode.LADD); }
	public InsnBuilder fadd() { return _unsupported(Opcode.FADD); }
	public InsnBuilder dadd() { return _unsupported(Opcode.DADD); }

	public InsnBuilder isub() { return _unsupported(Opcode.ISUB); }
	public InsnBuilder lsub() { return _unsupported(Opcode.LSUB); }
	public InsnBuilder fsub() { return _unsupported(Opcode.FSUB); }
	public InsnBuilder dsub() { return _unsupported(Opcode.DSUB); }

	public InsnBuilder imul() { return _unsupported(Opcode.IMUL); }
	public InsnBuilder lmul() { return _unsupported(Opcode.LMUL); }
	public InsnBuilder fmul() { return _unsupported(Opcode.FMUL); }
	public InsnBuilder dmul() { return _unsupported(Opcode.DMUL); }

	public InsnBuilder idiv() { return _unsupported(Opcode.IDIV); }
	public InsnBuilder ldiv() { return _unsupported(Opcode.LDIV); }
	public InsnBuilder fdiv() { return _unsupported(Opcode.FDIV); }
	public InsnBuilder ddiv() { return _unsupported(Opcode.DDIV); }

	public InsnBuilder irem() { return _unsupported(Opcode.IREM); }
	public InsnBuilder lrem() { return _unsupported(Opcode.LREM); }
	public InsnBuilder frem() { return _unsupported(Opcode.FREM); }
	public InsnBuilder drem() { return _unsupported(Opcode.DREM); }

	public InsnBuilder ineg() { return _unsupported(Opcode.INEG); }
	public InsnBuilder lneg() { return _unsupported(Opcode.LNEG); }
	public InsnBuilder fneg() { return _unsupported(Opcode.FNEG); }
	public InsnBuilder dneg() { return _unsupported(Opcode.DNEG); }

	public InsnBuilder ishl() { return _unsupported(Opcode.ISHL); }
	public InsnBuilder lshl() { return _unsupported(Opcode.LSHL); }
	public InsnBuilder ishr() { return _unsupported(Opcode.ISHR); }
	public InsnBuilder lshr() { return _unsupported(Opcode.LSHR); }

	public InsnBuilder iushr() { return _unsupported(Opcode.IUSHR); }
	public InsnBuilder lushr() { return _unsupported(Opcode.LUSHR); }

	public InsnBuilder iand() { return _unsupported(Opcode.IAND); }
	public InsnBuilder land() { return _unsupported(Opcode.LAND); }

	public InsnBuilder ior() { return _unsupported(Opcode.IOR); }
	public InsnBuilder lor() { return _unsupported(Opcode.LOR); }

	public InsnBuilder ixor() { return _unsupported(Opcode.IXOR); }
	public InsnBuilder lxor() { return _unsupported(Opcode.LXOR); }

	public InsnBuilder iinc() { return _unsupported(Opcode.IINC); }

	public InsnBuilder i2l() { return _unsupported(Opcode.I2L); }
	public InsnBuilder i2f() { return _unsupported(Opcode.I2F); }
	public InsnBuilder i2d() { return _unsupported(Opcode.I2D); }

	public InsnBuilder l2i() { return _unsupported(Opcode.L2I); }
	public InsnBuilder l2f() { return _unsupported(Opcode.L2F); }
	public InsnBuilder l2d() { return _unsupported(Opcode.L2D); }

	public InsnBuilder f2i() { return _unsupported(Opcode.F2I); }
	public InsnBuilder f2l() { return _unsupported(Opcode.F2L); }
	public InsnBuilder f2d() { return _unsupported(Opcode.F2D); }

	public InsnBuilder d2i() { return _unsupported(Opcode.D2I); }
	public InsnBuilder d2l() { return _unsupported(Opcode.D2L); }
	public InsnBuilder d2f() { return _unsupported(Opcode.D2F); }

	public InsnBuilder i2b() { return _unsupported(Opcode.I2B); }
	public InsnBuilder i2c() { return _unsupported(Opcode.I2C); }
	public InsnBuilder i2s() { return _unsupported(Opcode.I2S); }

	public InsnBuilder lcmp() { return _unsupported(Opcode.LCMP); }

	public InsnBuilder fcmpl() { return _unsupported(Opcode.FCMPL); }
	public InsnBuilder fcmpg() { return _unsupported(Opcode.FCMPG); }

	public InsnBuilder dcmpl() { return _unsupported(Opcode.DCMPL); }
	public InsnBuilder dcmpg() { return _unsupported(Opcode.DCMPG); }

	public InsnBuilder ifeq() { return _unsupported(Opcode.IFEQ); }
	public InsnBuilder ifne() { return _unsupported(Opcode.IFNE); }

	public InsnBuilder iflt() { return _unsupported(Opcode.IFLT); }
	public InsnBuilder ifge() { return _unsupported(Opcode.IFGE); }
	public InsnBuilder ifgt() { return _unsupported(Opcode.IFGT); }
	public InsnBuilder ifle() { return _unsupported(Opcode.IFLE); }

	public InsnBuilder if_icmpeq() { return _unsupported(Opcode.IF_ICMPEQ); }
	public InsnBuilder if_icmpne() { return _unsupported(Opcode.IF_ICMPNE); }
	public InsnBuilder if_icmplt() { return _unsupported(Opcode.IF_ICMPLT); }
	public InsnBuilder if_icmpge() { return _unsupported(Opcode.IF_ICMPGE); }
	public InsnBuilder if_icmpgt() { return _unsupported(Opcode.IF_ICMPGT); }
	public InsnBuilder if_icmple() { return _unsupported(Opcode.IF_ICMPLE); }
	public InsnBuilder if_acmpeq() { return _unsupported(Opcode.IF_ACMPEQ); }
	public InsnBuilder if_acmpne() { return _unsupported(Opcode.IF_ACMPNE); }

	public InsnBuilder GOTO() { return _unsupported(Opcode.GOTO); }

	public InsnBuilder jsr() { return _unsupported(Opcode.JSR); }

	public InsnBuilder ret() { return _unsupported(Opcode.RET); }

	public InsnBuilder tableswitch() { return _unsupported(Opcode.TABLESWITCH); }
	public InsnBuilder lookupswitch() { return _unsupported(Opcode.LOOKUPSWITCH); }

	public InsnBuilder ireturn() { return _unsupported(Opcode.IRETURN); }
	public InsnBuilder lreturn() { return _unsupported(Opcode.LRETURN); }
	public InsnBuilder freturn() { return _unsupported(Opcode.FRETURN); }
	public InsnBuilder dreturn() { return _unsupported(Opcode.DRETURN); }
	public InsnBuilder areturn() { return _unsupported(Opcode.ARETURN); }
	public InsnBuilder RETURN() { return _unsupported(Opcode.RETURN); }

	public InsnBuilder getstatic() { return _unsupported(Opcode.GETSTATIC); }
	public InsnBuilder putstatic() { return _unsupported(Opcode.PUTSTATIC); }

	public InsnBuilder getfield() { return _unsupported(Opcode.GETFIELD); }
	public InsnBuilder putfield() { return _unsupported(Opcode.PUTFIELD); }

	public InsnBuilder invokevirtual() { return _unsupported(Opcode.INVOKEVIRTUAL); }
	public InsnBuilder invokespecial() { return _unsupported(Opcode.INVOKESPECIAL); }
	public InsnBuilder invokestatic() { return _unsupported(Opcode.INVOKESTATIC); }
	public InsnBuilder invokeinterface() { return _unsupported(Opcode.INVOKEINTERFACE); }

	public InsnBuilder xxxunusedxxx1() { return _unsupported(Opcode.XXXUNUSEDXXX1); }

	public InsnBuilder NEW() { return _unsupported(Opcode.NEW); }

	public InsnBuilder newarray() { return _unsupported(Opcode.NEWARRAY); }
	public InsnBuilder anewarray() { return _unsupported(Opcode.ANEWARRAY); }
	public InsnBuilder arraylength() { return _unsupported(Opcode.ARRAYLENGTH); }

	public InsnBuilder athrow() { return _unsupported(Opcode.ATHROW); }

	public InsnBuilder checkcast() { return _unsupported(Opcode.CHECKCAST); }
	public InsnBuilder INSTANCEOF() { return _unsupported(Opcode.INSTANCEOF); }

	public InsnBuilder monitorenter() { return _unsupported(Opcode.MONITORENTER); }
	public InsnBuilder monitorexit() { return _unsupported(Opcode.MONITOREXIT); }

	public InsnBuilder wide() { return _unsupported(Opcode.WIDE); }

	public InsnBuilder multianewarray() { return _unsupported(Opcode.MULTIANEWARRAY); }

	public InsnBuilder ifnull() { return _unsupported(Opcode.IFNULL); }
	public InsnBuilder ifnonnull() { return _unsupported(Opcode.IFNONNULL); }

	public InsnBuilder goto_w() { return _unsupported(Opcode.GOTO_W); }
	public InsnBuilder jsr_w() { return _unsupported(Opcode.JSR_W); }



	public InsnBuilder breakpoint() { return _unsupported(Opcode.BREAKPOINT); }
	public InsnBuilder impdep1() { return _unsupported(Opcode.IMPDEP1); }
	public InsnBuilder impdep2() { return _unsupported(Opcode.IMPDEP2); }

}
