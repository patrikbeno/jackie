package org.jackie.compiler.bytecode;

import org.jackie.compiler.filemanager.ClassPathFileManager;
import org.jackie.compiler.filemanager.FileManager;
import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.typeregistry.JClassRegistry;
import org.jackie.compiler.typeregistry.MultiRegistry;
import org.jackie.compiler.typeregistry.PrimitiveTypeRegistry;
import org.jackie.compiler.typeregistry.TypeRegistry;
import org.jackie.compiler.util.Context;
import static org.jackie.compiler.util.Context.context;
import org.jackie.compiler.util.PathName;
import org.jackie.utils.Assert;

import org.objectweb.asm.ClassReader;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Patrik Beno
 */
@Test
public class ClassReaderTest {

	public void readSample() throws Exception {

		FileManager fm = new ClassPathFileManager();
		TypeRegistry tr = new MultiRegistry(
				new PrimitiveTypeRegistry(),
				new JClassRegistry(extractClassNames(fm.getPathNames()))
		);

		Context.createContext(tr);

		ClassReader cr = new ClassReader(
				getClass().getResourceAsStream(Sample.class.getSimpleName() + ".class"));
		JClassReader r = new JClassReader();
		cr.accept(r, ClassReader.SKIP_CODE);

		JClassImpl jclass = context().typeRegistry().getJClass(Sample.class);
		Assert.expected(Sample.class.getSimpleName(), jclass.name, "invalid simple class name");
		Assert.expected(Sample.class.getName(), jclass.getFQName(), "invalid class fqname");

	}

	Set<String> extractClassNames(Set<String> pathnames) {
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
