package org.jackie.compiler.bytecode;

import org.jackie.compiler.jmodelimpl.FlagsImpl;
import org.jackie.compiler.jmodelimpl.attribute.impl.Kind;
import org.jackie.compiler.util.ClassName;
import org.jackie.jmodel.props.AccessMode;
import org.jackie.jmodel.props.Flag;
import org.jackie.jmodel.JClass;
import org.jackie.utils.Assert;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.util.Set;

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

	private int check(FlagsImpl flags, Flag flag, int access) {
		return flags.isSet(flag) ? access : 0;
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

	protected int toAccessFlags(JClass jcls) {
		throw Assert.notYetImplemented(); // fixme JModelUtils obsoleted
//		int access = 0;
//		access |= JModelUtils.isInterface(jcls) ? Opcodes.ACC_INTERFACE : 0;
//		access |= JModelUtils.isAnnotation(jcls) ? Opcodes.ACC_ANNOTATION : 0;
//		access |= JModelUtils.isEnum(jcls) ? Opcodes.ACC_ENUM : 0;
//		return access;
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
