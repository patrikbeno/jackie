package org.jackie.asmtools;

import static org.jackie.utils.JavaHelper.isSet;
import org.objectweb.asm.Opcodes;

/**
 * @author Patrik Beno
 */
public class MethodInfo {

	public MethodBuilder methodBuilder;

	public String name;
	public Class type;
	public Class[] parameters;
	public Class[] exceptions;

	public int flags;
	public Variables variables;

	{
		variables = new Variables();
	}

	public MethodInfo(MethodBuilder methodBuilder,
							String name, Class type,
							Class[] parameters, Class[] exceptions,
							int flags)
	{
		this.methodBuilder = methodBuilder;

		this.name = name;
		this.type = type;
		this.parameters = parameters;
		this.exceptions = exceptions;
		this.flags = flags;
	}


	public boolean isStatic() {
		return isSet(flags, Opcodes.ACC_STATIC);
	}
}
