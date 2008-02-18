package org.jackie.compiler.bytecode;

import org.jackie.compiler.jmodelimpl.FlagsImpl;
import org.jackie.compiler.util.ClassName;
import org.jackie.jmodel.AccessMode;
import org.jackie.jmodel.Flag;
import org.jackie.utils.Assert;

import org.objectweb.asm.Opcodes;
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

	protected FlagsImpl toFlags(int access) {
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
		return flags;
	}

	private void checkAndSet(FlagsImpl flags, int access, int asmflag, Flag flag) {
		if (isSet(access, asmflag)) {
			flags.set(flag);
		}
	}
}
