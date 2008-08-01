package org.jackie.compiler_impl.bytecode;

import org.jackie.context.ContextObject;
import static org.jackie.context.ContextManager.context;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

/**
 * @author Patrik Beno
 */
public class BCClassContext implements ContextObject {

    static public BCClassContext instance() {
        return context(BCClassContext.class);
    }

    public ClassVisitor classVisitor;

	public MethodVisitor methodVisitor;
	
}
