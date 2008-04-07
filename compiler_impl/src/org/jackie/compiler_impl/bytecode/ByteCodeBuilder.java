package org.jackie.compiler_impl.bytecode;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import static org.jackie.context.ContextManager.context;
import org.jackie.utils.Assert;
import org.jackie.utils.LightList;
import org.jackie.jvm.structure.JMethod;
import org.jackie.jvm.structure.JField;
import org.jackie.jvm.JClass;

import java.util.List;

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

	protected String bcName(JClass jcls) {
		return jcls.getFQName().replace('.', '/');
	}

	protected String[] bcNames(Iterable<JClass> jclasses) {
		List<String> bcnames = new LightList<String>();
		for (JClass cls : jclasses) {
			bcnames.add(bcName(cls));
		}
		return bcnames.toArray(new String[bcnames.size()]);
	}

	protected String bcDesc(JField jfield) {
		throw Assert.notYetImplemented(); // todo implement this
	}

	protected String bcDesc(JMethod jmethod) {
		throw Assert.notYetImplemented(); // todo implement this
	}

	protected String bcSignature(JField jfield) {
		throw Assert.notYetImplemented(); // todo implement this
	}

	protected String bcSignature(JMethod jmethod) {
		throw Assert.notYetImplemented(); // todo implement this
	}
}
