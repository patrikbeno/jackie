package org.jackie.compiler;

import org.jackie.compiler.bytecode.JClassReader;
import org.jackie.compiler.filemanager.ClassPathFileManager;
import org.jackie.compiler.filemanager.FileManager;
import org.jackie.compiler.typeregistry.JClassRegistry;
import org.jackie.compiler.typeregistry.MultiRegistry;
import org.jackie.compiler.typeregistry.PrimitiveTypeRegistry;
import org.jackie.compiler.typeregistry.TypeRegistry;
import org.jackie.compiler.typeregistry.EditAction;
import org.jackie.compiler.util.Context;
import org.jackie.compiler.util.PathName;
import static org.jackie.compiler.util.Context.context;
import org.jackie.compiler.jmodelimpl.LoadLevel;
import org.jackie.utils.Assert;
import org.jackie.utils.Log;

import org.objectweb.asm.ClassReader;

import java.io.IOException;
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
