package org.jackie.compiler_impl.bytecode;

import org.jackie.compiler.typeregistry.TypeRegistry;
import static org.jackie.context.ContextManager.context;
import org.jackie.jvm.JClass;
import org.jackie.utils.ClassName;
import org.objectweb.asm.Type;

/**
 * @author Patrik Beno
 */
public class ByteCodeLoader extends AsmSupport {

	protected JClass getJClass(ClassName clsname) {
		return context(TypeRegistry.class).getJClass(clsname);
	}

	protected JClass getJClassByBName(String bname) {
		return context(TypeRegistry.class).getJClass(getClassName(bname));
	}

	protected JClass getJClassByDesc(String desc) {
		return context(TypeRegistry.class).getJClass(getClassNameByDesc(desc));
	}

	protected JClass getJClassByType(Type type) {
		return context(TypeRegistry.class).getJClass(new ClassName(type.getClassName()));
	}

}
