package org.jackie.asmtools;

import static org.jackie.utils.JavaHelper.isSet;
import org.objectweb.asm.Opcodes;

import java.util.Formattable;
import java.util.Formatter;

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

	public String toString() {
		return String.format("%1$s(%3$s) : %2$s", name, type.getSimpleName(), new Formattable() {
			public void formatTo(Formatter formatter, int flags, int width, int precision) {
				String pattern = "%s";
				for (Class cls : parameters) {
					formatter.format(pattern, cls.getSimpleName());
					pattern = ", %s";
				}
			}
		});
	}
}
