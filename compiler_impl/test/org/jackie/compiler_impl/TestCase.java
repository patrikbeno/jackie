package org.jackie.compiler_impl;

import org.jackie.compiler.filemanager.FileManager;
import org.jackie.compiler.typeregistry.TypeRegistry;
import org.jackie.compiler_impl.filemanager.ClassPathFileManager;
import org.jackie.compiler_impl.typeregistry.JClassRegistry;
import org.jackie.compiler_impl.typeregistry.MultiRegistry;
import org.jackie.compiler_impl.typeregistry.PrimitiveTypeRegistry;
import static org.jackie.context.ContextManager.*;

/**
 * @author Patrik Beno
 */
public abstract class TestCase {

	protected FileManager filemanager;
	protected TypeRegistry typeregistry;

	{
		filemanager = new ClassPathFileManager();
		typeregistry = new MultiRegistry(
				new PrimitiveTypeRegistry(),
				new JClassRegistry(filemanager)
		);

	}

	protected void run(Runnable r) {
		newContext();
		try {
			context().set(TypeRegistry.class, typeregistry);
			r.run();
		} finally {
			closeContext();
		}

	}

}
