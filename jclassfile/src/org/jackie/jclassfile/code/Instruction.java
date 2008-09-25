package org.jackie.jclassfile.code;

import org.jackie.utils.Chain;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.DataOutput;
import java.io.DataInput;
import java.util.List;

/**
 * @author Patrik Beno
 */
public interface Instruction extends Chain<Instruction> {

	int opcode();

	/**
	 * size in bytes, including opcode
	 * @return
	 */
	int size();

	/**
	 * computes instruction offset in current instruction sequence (considers individual instruction
	 * size()).
	 * @return
	 */
	int offset();

	List<Instruction> asList();

	void save(DataOutput out) throws IOException;
}
