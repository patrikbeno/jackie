package org.jackie.compiler_impl.bytecode;

import org.jackie.jvm.JClass;
import org.jackie.jclassfile.util.TypeDescriptor;
import org.jackie.jclassfile.util.ClassNameHelper;
import org.jackie.utils.ClassName;
import org.jackie.compiler.typeregistry.TypeRegistry;
import static org.jackie.context.ContextManager.context;

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
