package org.jackie.asmtools;

import org.objectweb.asm.Label;

/**
 * @author Patrik Beno
 */
public class Variable {

	String name;
	Class type;
	int index;

	Label start;
	Label end;

	boolean parameter;
	boolean synthetic;

	public Variable(String name, Class type, int index,
						 Label start, Label end,
						 boolean parameter, boolean synthetic)
	{
		this.name = name;
		this.type = type;
		this.index = index;
		this.start = start;
		this.end = end;
		this.parameter = parameter;
		this.synthetic = synthetic;
	}
}
