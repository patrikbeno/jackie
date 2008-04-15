package org.jackie.compiler_impl.bytecode;

import org.jackie.context.ContextObject;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

/**
 * @author Patrik Beno
 */
public class BCClassContext implements ContextObject {

	public ClassVisitor classVisitor;

	public MethodVisitor methodVisitor;
	
}
