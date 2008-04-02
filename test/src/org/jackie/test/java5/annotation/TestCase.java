package org.jackie.test.java5.annotation;

import org.jackie.compiler.filemanager.FileManager;
import org.jackie.compiler.typeregistry.TypeRegistry;
import org.jackie.compiler_impl.filemanager.ClassPathFileManager;
import org.jackie.compiler_impl.typeregistry.MultiRegistry;
import org.jackie.compiler_impl.typeregistry.PrimitiveTypeRegistry;
import org.jackie.compiler_impl.typeregistry.JClassRegistry;
import org.jackie.compiler_impl.util.PathName;
import static org.jackie.context.ContextManager.newContext;
import static org.jackie.context.ContextManager.context;
import static org.jackie.context.ContextManager.closeContext;

import java.util.Set;
import java.util.HashSet;

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
				new JClassRegistry(filemanager, extractClassNames(filemanager.getPathNames()))
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


	protected Set<String> extractClassNames(Set<String> pathnames) {
		Set<String> classes = new HashSet<String>(pathnames.size());
		for (String s : pathnames) {
			PathName pathname = new PathName(s);
			if (pathname.isClass()) {
				classes.add(pathname.getClassName());
			}
		}
		return classes;
	}

}
