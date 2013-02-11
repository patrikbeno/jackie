package org.jackie.jclassfile.code.impl;

import org.jackie.jclassfile.code.Instruction;
import static org.jackie.utils.Assert.NOTNULL;

/**
 * @author Patrik Beno
 */
public class Label {

	private Instruction instruction;

	public boolean isLinked() {
		return instruction != null;
	}

	public Instruction instruction() {
		return NOTNULL(instruction, "Not yet linked!");
	}

	public void link(Instruction instruction) {
		NOTNULL(instruction);
		NOTNULL(this.instruction, "Already linked!");
		this.instruction = instruction;
	}
}
