package org.jackie.compiler_impl.bytecode;

import static org.jackie.context.ContextManager.context;
import org.jackie.jvm.JClass;
import org.jackie.jvm.JNode;
import static org.jackie.utils.Assert.typecast;
import org.jackie.jclassfile.util.ClassNameHelper;
import org.jackie.compiler.spi.Compilable;

/**
 * @author Patrik Beno
 */
public abstract class ByteCodeBuilder {

	static public void execute(ByteCodeBuilder builder) {
		builder.init();
		builder.run();
	}

	protected ByteCodeBuilder() {
	}

	protected void init() {
	}

	protected abstract void run();

	protected String toBinaryClassName(JClass jclass) {
		return ClassNameHelper.toBinaryClassName(jclass.getFQName());
	}

	protected void compile(JNode jnode) {
		typecast(jnode, Compilable.class).compile();
	}
}