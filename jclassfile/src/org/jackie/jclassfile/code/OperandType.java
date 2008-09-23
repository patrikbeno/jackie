package org.jackie.jclassfile.code;

import java.util.BitSet;

/**
 * @author Patrik Beno
 */
public enum OperandType {

	// constants
	BYTE,

	// frame reference (stack, method parameters, local variables)
	FRAMEREF,

	// pool references
	FIELDREF,
	METHODREF,
	CLASSREF,

	// jump/goto/etc
	BRANCHOFFSET,

}
