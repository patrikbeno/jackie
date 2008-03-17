package org.jackie.compiler_impl;

import org.jackie.compiler_impl.filemanager.ClassPathFileManager;
import org.jackie.compiler.filemanager.FileManager;
import org.jackie.compiler_impl.typeregistry.JClassRegistry;
import org.jackie.compiler_impl.typeregistry.MultiRegistry;
import org.jackie.compiler_impl.typeregistry.PrimitiveTypeRegistry;
import org.jackie.compiler.typeregistry.TypeRegistry;
import org.jackie.compiler.Context;
import org.jackie.compiler_impl.util.PathName;
import static org.jackie.compiler.Context.context;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public abstract class TestCase {

	protected FileManager filemanager;
	protected TypeRegistry typeregistry;

	{
		Context.createContext();

		filemanager = new ClassPathFileManager();
		typeregistry = new MultiRegistry(
				new PrimitiveTypeRegistry(),
				new JClassRegistry(filemanager, extractClassNames(filemanager.getPathNames()))
		);

		context().typeRegistry(typeregistry);
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
