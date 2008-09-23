package org.jackie.jclassfile.code;

import org.jackie.jclassfile.constantpool.impl.ClassRef;
import org.jackie.jclassfile.constantpool.impl.FieldRef;
import org.jackie.jclassfile.constantpool.impl.MethodRef;
import static org.jackie.jclassfile.ClassFileContext.classFileContext;
import static org.jackie.jclassfile.code.InstructionFactoryManager.instructionFactoryManager;

import java.io.IOException;
import java.io.DataInput;
import java.util.BitSet;
import java.util.Arrays;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class FieldRefInstructionFactory implements InstructionFactory {

	static class FieldRefInstruction extends PoolRefInstruction<FieldRef> {
		FieldRefInstruction(int opcode, FieldRef constant) {
			super(opcode, constant);
		}
		protected String operandToString() {
			return String.format("%s %s %s", constant.classref().value(), constant.nametype().name(), constant.nametype().stype());
		}
	}

	static BitSet supported = new BitSet(0xFF);

	static {
		List<OperandType> operands = Arrays.asList(OperandType.FIELDREF);
		for (Opcode o : Opcode.values()) {
			if (o.operands().equals(operands)) {
				supported.set(o.opcode());
			}
		}
	}

	{
		instructionFactoryManager().registerFactory(supported, this);
	}


	public Instruction loadInstruction(int opcode, DataInput in) throws IOException {
		assert supported.get(opcode) : String.format("Unsupported opcode: %s", opcode);

		int index = in.readUnsignedShort();
		FieldRef ref = classFileContext().constantPool().getConstant(index, FieldRef.class);
		return new FieldRefInstruction(opcode, ref);
	}

}