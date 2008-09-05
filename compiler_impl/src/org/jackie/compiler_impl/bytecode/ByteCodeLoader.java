package org.jackie.compiler_impl.bytecode;

import org.jackie.compiler.typeregistry.TypeRegistry;
import static org.jackie.context.ContextManager.context;
import org.jackie.jclassfile.util.ClassNameHelper;
import org.jackie.jclassfile.util.TypeDescriptor;
import org.jackie.jvm.JClass;
import org.jackie.utils.ClassName;

/**
 * @author Patrik Beno
 */
public abstract class ByteCodeLoader {

	static public void execute(ByteCodeLoader loader) {
		loader.run();
	}

	protected abstract void run();

	protected JClass getJClass(TypeDescriptor descriptor) {
		String bname = ClassNameHelper.toJavaClassName(descriptor.getTypeName());
		ClassName clsname = new ClassName(bname, descriptor.getDimensions());
		return typeRegistry().getJClass(clsname);
	}

	protected TypeRegistry typeRegistry() {
		return context(TypeRegistry.class);
	}

}
