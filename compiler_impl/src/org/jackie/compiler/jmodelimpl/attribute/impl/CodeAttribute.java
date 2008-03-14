package org.jackie.compiler.jmodelimpl.attribute.impl;

import org.jackie.jmodel.attribute.JAttribute;
import org.objectweb.asm.tree.MethodNode;

/**
 * @author Patrik Beno
 */
public class CodeAttribute implements JAttribute {

	public MethodNode code;

	public CodeAttribute(MethodNode code) {
		this.code = code;
	}

	public String getName() {
		return "Code";
	}

}
