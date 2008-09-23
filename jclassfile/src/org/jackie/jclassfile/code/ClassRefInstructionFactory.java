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
public class ClassRefInstructionFactory implements InstructionFactory {

	static class ClassRefInstruction extends PoolRefInstruction<ClassRef> {
		ClassRefInstruction(int opcode, ClassRef constant) {
			super(opcode, constant);
		}

		protected String operandToString() {
			return String.format("%s", constant.value());
		}
	}

	static BitSet supported = new BitSet(0xFF);

	static {
		List<OperandType> operands = Arrays.asList(OperandType.CLASSREF);
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
		ClassRef ref = classFileContext().constantPool().getConstant(index, ClassRef.class);
		return new ClassRefInstruction(opcode, ref);
	}

}
