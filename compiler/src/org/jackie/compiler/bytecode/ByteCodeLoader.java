package org.jackie.compiler.bytecode;

import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.util.ClassName;
import static org.jackie.compiler.util.Context.context;
import org.jackie.jmodel.JClass;

import org.objectweb.asm.Type;

/**
 * @author Patrik Beno
 */
public class ByteCodeLoader extends AsmSupport {

	protected JClass getJClass(ClassName clsname) {
		return context().typeRegistry().getJClass(clsname);
	}

	protected JClass getJClassByBName(String bname) {
		return context().typeRegistry().getJClass(getClassName(bname));
	}

	protected JClass getJClassByDesc(String desc) {
		return context().typeRegistry().getJClass(getClassNameByDesc(desc));
	}

	protected JClass getJClassByType(Type type) {
		return context().typeRegistry().getJClass(new ClassName(type));
	}

}
