package org.jackie.compiler_impl.bytecode;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import static org.jackie.context.ContextManager.context;
import org.jackie.utils.Assert;
import org.jackie.utils.LightList;
import org.jackie.utils.ClassName;
import static org.jackie.utils.Assert.typecast;
import org.jackie.jvm.structure.JMethod;
import org.jackie.jvm.structure.JField;
import org.jackie.jvm.JClass;
import org.jackie.jvm.extension.builtin.PrimitiveType;
import org.jackie.jvm.extension.builtin.ArrayType;
import org.jackie.jvm.props.Flags;
import org.jackie.jvm.props.Flag;
import org.jackie.jvm.props.AccessMode;
import org.jackie.compiler_impl.jmodelimpl.FlagsImpl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * @author Patrik Beno
 */
public abstract class ByteCodeBuilder extends AsmSupport {

	static final private Map<String,String> MAPPING = new HashMap<String, String>() {{
			put("void", 	"V");
			put("char", 	"C");
			put("byte", 	"B");
			put("short", 	"S");
			put("int", 		"I");
			put("long", 	"J");
			put("float", 	"F");
			put("double", 	"D");
	}};

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

	protected String bcDesc(JClass jcls) {
		StringBuilder sb = new StringBuilder();

		if (jcls.extensions().supports(PrimitiveType.class)) {
			sb.append(MAPPING.get(jcls.getName()));

		} else if (jcls.extensions().supports(ArrayType.class)) {
			ArrayType array = jcls.extensions().get(ArrayType.class);
			sb.append(bcDesc(array.getComponentType()));
			sb.append("[");

		} else {
			// regular Object
			sb.append("L").append(jcls.getFQName().replace(".", "/"));
		}

		sb.append(";");

		return sb.toString();
	}

	protected String bcDesc(JField jfield) {
		return bcDesc(jfield.getJClass());
	}

	protected String bcDesc(JMethod jmethod) {
		return bcDesc(jmethod.getJClass());
	}

	/// flags


	private int check(FlagsImpl flags, Flag flag, int access) {
		return flags.isSet(flag) ? access : 0;
	}

	protected int toAccessFlag(Flags flags) {
		return toAccessFlag(typecast(flags, FlagsImpl.class));
	}

	protected int toAccessFlag(FlagsImpl flags) {
		int access = 0;
		access |= check(flags, Flag.ABSTRACT, Opcodes.ACC_ABSTRACT);
		access |= check(flags, Flag.STATIC, Opcodes.ACC_STATIC);
		access |= check(flags, Flag.FINAL, Opcodes.ACC_FINAL);
		access |= check(flags, Flag.TRANSIENT, Opcodes.ACC_TRANSIENT);
		access |= check(flags, Flag.VOLATILE, Opcodes.ACC_VOLATILE);
		access |= check(flags, Flag.NATIVE, Opcodes.ACC_NATIVE);
		access |= check(flags, Flag.SYNCHRONIZED, Opcodes.ACC_SYNCHRONIZED);
		access |= check(flags, Flag.STRICTFP, Opcodes.ACC_STRICT);
		access |= check(flags, Flag.DEPRECATED, Opcodes.ACC_DEPRECATED);
		access |= check(flags, Flag.BRIDGE, Opcodes.ACC_BRIDGE);
		access |= check(flags, Flag.SYNTHETIC, Opcodes.ACC_SYNTHETIC);
		return access;
	}

	protected int toAccessFlag(AccessMode mode) {
		int access = 0;
		access |= AccessMode.PRIVATE.equals(mode) ? Opcodes.ACC_PRIVATE : 0;
		access |= AccessMode.PROTECTED.equals(mode) ? Opcodes.ACC_PROTECTED : 0;
		access |= AccessMode.PUBLIC.equals(mode) ? Opcodes.ACC_PUBLIC : 0;
		// nothing else to do
		return access;
	}
	
}
