package org.jackie.jclassfile.code;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.DataInput;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public interface InstructionFactory {

	Instruction loadInstruction(int opcode, DataInput in) throws IOException;
}
