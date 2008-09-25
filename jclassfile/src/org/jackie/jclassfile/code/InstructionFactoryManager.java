package org.jackie.jclassfile.code;

import static org.jackie.utils.Assert.NOTNULL;
import static org.jackie.context.ContextManager.context;
import org.jackie.context.ContextObject;
import org.jackie.jclassfile.code.impl.Opcode;

import java.util.BitSet;

/**
 * @author Patrik Beno
 */
public class InstructionFactoryManager implements ContextObject {

	static public InstructionFactoryManager instructionFactoryManager() {
		InstructionFactoryManager ifm = context(InstructionFactoryManager.class);
		if (ifm == null) {
			ifm = new InstructionFactoryManager();
			context().set(InstructionFactoryManager.class, ifm);

			// fixme QDH
			Opcode.registerInstructionFactories();
		}
		return NOTNULL(context(InstructionFactoryManager.class));
	}

	InstructionFactory[] factoriesByOpcode;

	{
		factoriesByOpcode = new InstructionFactory[0xFF+1];
	}

	public void registerFactory(int opcode, InstructionFactory factory) {
		factoriesByOpcode[opcode] = factory; // todo validations
	}

	public void registerFactory(BitSet opcodes, InstructionFactory factory) {
		for (int opcode = 0; (opcode = opcodes.nextSetBit(opcode)) >= 0; opcode++) {
			registerFactory(opcode, factory);
		}
	}

	public InstructionFactory getFactory(int opcode) {
		return NOTNULL(factoriesByOpcode[opcode], "No InstructionFactory for opcode: 0x%X", opcode);
	}
}
