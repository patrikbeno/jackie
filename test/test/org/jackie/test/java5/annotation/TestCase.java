package org.jackie.test.java5.annotation;

import org.jackie.compiler.extension.ExtensionManager;
import org.jackie.compiler.filemanager.FileManager;
import org.jackie.compiler.typeregistry.TypeRegistry;
import org.jackie.compiler.spi.DefaultExtensionManagerConfigurator;
import org.jackie.compilerimpl.ext.ExtensionManagerImpl;
import org.jackie.compilerimpl.filemanager.ClassPathFileManager;
import org.jackie.compilerimpl.typeregistry.JClassRegistry;
import org.jackie.compilerimpl.typeregistry.MultiRegistry;
import org.jackie.compilerimpl.typeregistry.PrimitiveTypeRegistry;
import org.jackie.compilerimpl.jmodelimpl.JClassImpl;
import static org.jackie.context.ContextManager.*;
import org.jackie.utils.Assert;
import org.jackie.jvm.JClass;
import org.jackie.jclassfile.model.ClassFile;
import org.jackie.test.jclassfile.Util;

/**
 * @author Patrik Beno
 */
public abstract class TestCase {

	protected FileManager filemanager;
	protected TypeRegistry typeregistry;
	protected ExtensionManager extmanager;

	{
		try {
			filemanager = new ClassPathFileManager();
			typeregistry = new MultiRegistry(
					new PrimitiveTypeRegistry(),
					new JClassRegistry(filemanager)
			);
			extmanager = new ExtensionManagerImpl();
		} catch (Throwable t) {
			throw Assert.unexpected(t);
		}
	}

	protected void run(Runnable r) {
		newContext();
		try {
			context().set(TypeRegistry.class, typeregistry);
			context().set(ExtensionManager.class, extmanager);
			DefaultExtensionManagerConfigurator.configure();

			r.run();

		} finally {
			closeContext();
		}
	}

	protected JClass jclass(Class cls) {
		return typeregistry.getJClass(cls);
	}

	protected ClassFile compile(JClass jclass) {
		ClassFile classfile = new ClassFile();
		((JClassImpl)jclass).compile(classfile);
		return classfile;
	}

	protected ClassFile compile(Class cls) {
		return compile(jclass(cls));
	}

	protected ClassFile load(Class cls) {
		return Util.parseClassFile(Util.getByteCode(cls));
	}

	protected ClassFile javac(Class cls) {
		return Util.parseClassFile(Util.getByteCode(cls));
	}

	protected ClassFile jackie(Class cls) {
		return compile(cls);
	}

}
