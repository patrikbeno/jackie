package org.jackie.jclassfile.code;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.DataOutput;

/**
 * @author Patrik Beno
 */
public interface Instruction {

	int opcode();

	/**
	 * size in bytes, including opcode
	 * @return
	 */
	int size();

	void save(DataOutput out) throws IOException;

}
