package org.jackie.compiler.jmodelimpl.type;

import org.jackie.compiler.bytecode.AsmSupport;
import org.jackie.compiler.jmodelimpl.JClassImpl;

import org.objectweb.asm.Opcodes;

/**
 * @author Patrik Beno
 */
public class SpecialTypes extends AsmSupport {
	public void applySpecialTypes(JClassImpl jclass, int access) {

		jclass.addCapability(new ClassTypeImpl());

		if (isSet(access, Opcodes.ACC_INTERFACE)) {
			jclass.addCapability(new InterfaceTypeImpl());

		}
		if (isSet(access, Opcodes.ACC_ANNOTATION)) {
			jclass.addCapability(new AnnotationTypeImpl(jclass));

		}
		if (isSet(access, Opcodes.ACC_ENUM)) {
			jclass.addCapability(new EnumTypeImpl(jclass));

		}

	}
}
