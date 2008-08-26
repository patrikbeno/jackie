package org.jackie.compiler_impl.bytecode;

import org.jackie.compiler_impl.jmodelimpl.FlagsImpl;
import static org.jackie.context.ContextManager.context;
import org.jackie.jvm.JClass;
import org.jackie.jvm.JNode;
import org.jackie.jvm.extension.builtin.ArrayType;
import org.jackie.jvm.extension.builtin.PrimitiveType;
import org.jackie.jvm.props.AccessMode;
import org.jackie.jvm.props.Flag;
import org.jackie.jvm.props.Flags;
import org.jackie.jvm.structure.JField;
import org.jackie.jvm.structure.JMethod;
import static org.jackie.utils.Assert.typecast;
import org.jackie.utils.LightList;
import org.jackie.jclassfile.util.ClassNameHelper;
import org.jackie.compiler.spi.Compilable;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Patrik Beno
 */
public abstract class ByteCodeBuilder2 {

	static public void execute(ByteCodeBuilder2 builder) {
		builder.init();
		builder.run();
	}

	protected ByteCodeBuilder2() {
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