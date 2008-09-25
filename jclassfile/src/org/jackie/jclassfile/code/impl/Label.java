package org.jackie.jclassfile.code.impl;

import org.jackie.jclassfile.code.Instruction;

/**
 * @author Patrik Beno
 */
public class Label {

	private Instruction instruction;

	public Instruction instruction() {
		return instruction;
	}

	public void link(Instruction instruction) {
		this.instruction = instruction;
	}
}
