package org.jackie.compiler.util;

/**
 * @author Patrik Beno
 */
public class EnumProxy extends ClassProxy {

	public final String constant;

	public EnumProxy(String clsname, String constant) {
		super(clsname);
		this.constant = constant;
	}

	Enum asEnum() {
		Enum e = Enum.valueOf(asClass(), constant);
		return e;
	}
}
