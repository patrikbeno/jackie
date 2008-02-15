package org.jackie.compiler.bytecode;

import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.util.ClassName;
import static org.jackie.compiler.util.Context.typeRegistry;

import org.objectweb.asm.Type;

/**
 * @author Patrik Beno
 */
public class ByteCodeLoader extends AsmSupport {

	protected JClassImpl getJClassByBName(String bname) {
		return typeRegistry().getJClass(getClassName(bname));
	}

	protected JClassImpl getJClassByDesc(String desc) {
		return typeRegistry().getJClass(getClassNameByDesc(desc));
	}

	protected JClassImpl getJClassByType(Type type) {
		return typeRegistry().getJClass(new ClassName(type));
	}

}
