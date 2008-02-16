package org.jackie.compiler.common;

import org.jackie.compiler.bytecode.JClassReader;
import org.jackie.compiler.filemanager.ClassPathFileManager;
import org.jackie.compiler.filemanager.FileManager;
import org.jackie.compiler.typeregistry.JClassRegistry;
import org.jackie.compiler.typeregistry.MultiRegistry;
import org.jackie.compiler.typeregistry.PrimitiveTypeRegistry;
import org.jackie.compiler.typeregistry.TypeRegistry;
import org.jackie.compiler.util.Context;
import org.jackie.compiler.util.PathName;
import org.jackie.utils.Assert;

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
		filemanager = new ClassPathFileManager();
		typeregistry = new MultiRegistry(
				new PrimitiveTypeRegistry(),
				new JClassRegistry(extractClassNames(filemanager.getPathNames()))
		);

		Context.createContext(typeregistry);		
	}

	protected void readClass(Class cls) {
		try {
			ClassReader cr = new ClassReader(
					cls.getResourceAsStream(cls.getSimpleName() + ".class"));
			JClassReader r = new JClassReader();
			cr.accept(r, ClassReader.SKIP_CODE);
		} catch (IOException e) {
			throw Assert.notYetHandled(e);
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
