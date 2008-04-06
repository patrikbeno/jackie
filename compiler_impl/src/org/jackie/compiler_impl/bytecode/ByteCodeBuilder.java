package org.jackie.compiler_impl.bytecode;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import static org.jackie.context.ContextManager.context;
import org.jackie.utils.Assert;
import org.jackie.jvm.structure.JMethod;

/**
 * @author Patrik Beno
 */
public abstract class ByteCodeBuilder extends AsmSupport {

	{
		init();
		run();
	}

	protected void init() {}

	protected abstract void run();

	protected ClassVisitor cv() {
		return context(BCClassContext.class).classVisitor;
	}

	protected MethodVisitor mv() {
		return context(BCClassContext.class).methodVisitor;
	}

	protected String bcDesc(JMethod jmethod) {
		throw Assert.notYetImplemented(); // todo implement this
	}

	protected String bcSignature(JMethod jmethod) {
		throw Assert.notYetImplemented(); // todo implement this
	}
}
