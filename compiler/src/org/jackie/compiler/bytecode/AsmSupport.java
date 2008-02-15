package org.jackie.compiler.bytecode;

import org.jackie.compiler.util.ClassName;

import org.objectweb.asm.Type;

/**
 * @author Patrik Beno
 */
public class AsmSupport {

	protected ClassName getClassName(String bname) {
		return new ClassName(Type.getObjectType(bname));
	}

	protected ClassName getClassNameByDesc(String desc) {
		return new ClassName(Type.getType(desc));
	}

	protected boolean isSet(int access, int mask) {
		return (access & mask) == mask;
	}

}
