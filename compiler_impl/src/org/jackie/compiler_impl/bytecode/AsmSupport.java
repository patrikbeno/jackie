package org.jackie.compiler_impl.bytecode;

import org.jackie.compiler_impl.jmodelimpl.FlagsImpl;
import org.jackie.compiler.attribute.Kind;
import org.jackie.utils.ClassName;
import org.jackie.jvm.props.AccessMode;
import org.jackie.jvm.props.Flag;
import org.jackie.jvm.props.Flags;
import org.jackie.jvm.JClass;
import org.jackie.utils.Assert;
import static org.jackie.utils.Assert.typecast;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.util.Set;

/**
 * @author Patrik Beno
 */
public class AsmSupport {

	protected ClassName getClassName(String bname) {
		return new ClassName(Type.getObjectType(bname).getClassName());
	}

	protected ClassName getClassNameByDesc(String desc) {
		return new ClassName(Type.getType(desc).getClassName());
	}

	protected String bcName(JClass jcls) {
		return jcls.getFQName().replace('.', '/');
	}

	protected String bcDesc(JClass jcls) {
		return bcName(jcls); // fixme need to handle primitives/arrays/classes/...
	}

	protected boolean isSet(int access, int mask) {
		return (access & mask) == mask;
	}

	protected AccessMode toAccessMode(int access) {
		int mask = Opcodes.ACC_PRIVATE | Opcodes.ACC_PROTECTED | Opcodes.ACC_PUBLIC;
		switch (access & mask) {
			case Opcodes.ACC_PRIVATE:
				return AccessMode.PRIVATE;
			case Opcodes.ACC_PROTECTED:
				return AccessMode.PROTECTED;
			case Opcodes.ACC_PUBLIC:
				return AccessMode.PUBLIC;
			case 0:
				return AccessMode.PACKAGE;
			default:
				throw Assert.invariantFailed("Unhandled: %s", access);
		}
	}

	protected Flag[] toFlags(int access) {
		FlagsImpl flags = new FlagsImpl();
		checkAndSet(flags, access, Opcodes.ACC_ABSTRACT, Flag.ABSTRACT);
		checkAndSet(flags, access, Opcodes.ACC_STATIC, Flag.STATIC);
		checkAndSet(flags, access, Opcodes.ACC_FINAL, Flag.FINAL);
		checkAndSet(flags, access, Opcodes.ACC_TRANSIENT, Flag.TRANSIENT);
		checkAndSet(flags, access, Opcodes.ACC_VOLATILE, Flag.VOLATILE);
		checkAndSet(flags, access, Opcodes.ACC_NATIVE, Flag.NATIVE);
		checkAndSet(flags, access, Opcodes.ACC_SYNCHRONIZED, Flag.SYNCHRONIZED);
		checkAndSet(flags, access, Opcodes.ACC_STRICT, Flag.STRICTFP);
		checkAndSet(flags, access, Opcodes.ACC_DEPRECATED, Flag.DEPRECATED);
		checkAndSet(flags, access, Opcodes.ACC_BRIDGE, Flag.BRIDGE);
		checkAndSet(flags, access, Opcodes.ACC_SYNTHETIC, Flag.SYNTHETIC);
		Set<Flag> all = flags.getAllSet();
		Flag[] array = new Flag[all.size()];
		all.toArray(array);
		return array;
	}

	private void checkAndSet(FlagsImpl flags, int access, int asmflag, Flag flag) {
		if (isSet(access, asmflag)) {
			flags.set(flag);
		}
	}

	protected Kind toKind(int access) {
		if (isSet(access, Opcodes.ACC_ENUM)) {
			return Kind.ENUM;
		} else if (isSet(access, Opcodes.ACC_ANNOTATION)) {
			return Kind.ANNOTATION;
		} else if (isSet(access, Opcodes.ACC_INTERFACE)) {
			return Kind.INTERFACE;
		} else {
			return Kind.CLASS;
		}
	}

}
