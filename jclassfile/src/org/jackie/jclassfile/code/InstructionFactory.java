package org.jackie.jclassfile.code;

import org.jackie.jclassfile.constantpool.ConstantPool;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.DataInput;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public interface InstructionFactory {

	Instruction loadInstruction(int opcode, Instruction previous, DataInput in,
										 ConstantPool pool) throws IOException;
}
