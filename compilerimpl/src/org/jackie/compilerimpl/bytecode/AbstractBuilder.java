package org.jackie.compilerimpl.bytecode;

import org.jackie.compiler.typeregistry.TypeRegistry;
import static org.jackie.context.ContextManager.context;
import org.jackie.jclassfile.util.ClassNameHelper;
import org.jackie.jvm.JClass;
import org.jackie.utils.ClassName;

/**
 * @author Patrik Beno
 */
public abstract class AbstractBuilder {

	protected ClassName getClassNameFromBinaryName(String binaryClassName) {
		return getClassName(ClassNameHelper.toJavaClassName(binaryClassName));
	}

	protected ClassName getClassName(String clsname) {
		return new ClassName(clsname);
	}

	protected JClass getJClass(ClassName clsname) {
		return context(TypeRegistry.class).getJClass(clsname);
	}

	protected JClass getJClassFromBinaryName(String binaryClassName) {
		return getJClass(getClassNameFromBinaryName(binaryClassName));
	}

}
