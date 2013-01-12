package org.jackie.compilerimpl;

import org.jackie.compiler.filemanager.FileManager;
import org.jackie.compiler.typeregistry.TypeRegistry;
import org.jackie.compilerimpl.filemanager.ClassPathFileManager;
import org.jackie.compilerimpl.filemanager.InMemoryFileManager;
import org.jackie.compilerimpl.typeregistry.CompilerWorkspaceRegistry;
import org.jackie.compilerimpl.typeregistry.JClassRegistry;
import org.jackie.compilerimpl.typeregistry.MultiRegistry;
import org.jackie.compilerimpl.typeregistry.PrimitiveTypeRegistry;
import org.jackie.jclassfile.constantpool.Constant;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.model.ClassFile;
import org.jackie.jclassfile.model.FieldInfo;
import org.jackie.jclassfile.model.MethodInfo;
import org.jackie.utils.DataInputWrapper;

import static org.jackie.context.ContextManager.*;

import java.io.InputStream;
import java.io.PrintStream;

/**
 * @author Patrik Beno
 */
public abstract class TestCase {

	protected FileManager filemanager;
	protected TypeRegistry typeregistry;

	{
        filemanager = new InMemoryFileManager();
        typeregistry = new CompilerWorkspaceRegistry(
                new JClassRegistry(new InMemoryFileManager()),
                new MultiRegistry(
                        new PrimitiveTypeRegistry(),
                        new JClassRegistry(new ClassPathFileManager())
                )
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
