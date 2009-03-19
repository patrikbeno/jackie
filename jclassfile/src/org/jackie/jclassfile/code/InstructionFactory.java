package org.jackie.jclassfile.code;

import org.jackie.jclassfile.constantpool.ConstantPool;
import org.jackie.utils.XDataInput;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.DataInput;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public interface InstructionFactory {

	Instruction loadInstruction(int opcode,
										 CodeParser codeParser);
}
