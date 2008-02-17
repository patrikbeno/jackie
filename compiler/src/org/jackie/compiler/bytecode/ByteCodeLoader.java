package org.jackie.compiler.bytecode;

import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.util.ClassName;
import static org.jackie.compiler.util.Context.context;

import org.objectweb.asm.Type;

/**
 * @author Patrik Beno
 */
public class ByteCodeLoader extends AsmSupport {

	protected JClassImpl getJClass(ClassName clsname) {
		return context().typeRegistry().getJClass(clsname);
	}

	protected JClassImpl getJClassByBName(String bname) {
		return context().typeRegistry().getJClass(getClassName(bname));
	}

	protected JClassImpl getJClassByDesc(String desc) {
		return context().typeRegistry().getJClass(getClassNameByDesc(desc));
	}

	protected JClassImpl getJClassByType(Type type) {
		return context().typeRegistry().getJClass(new ClassName(type));
	}

}
