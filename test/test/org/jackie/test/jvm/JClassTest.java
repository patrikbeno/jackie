package org.jackie.test.jvm;

import com.sun.tools.javac.api.JavacTool;
import org.junit.Test;
import org.jackie.compiler.typeregistry.TypeRegistry;
import org.jackie.compiler.filemanager.FileManager;
import org.jackie.compiler.extension.ExtensionManager;
import org.jackie.compiler.context.CompilerContext;
import org.jackie.compilerimpl.typeregistry.CompilerWorkspaceRegistry;
import org.jackie.compilerimpl.typeregistry.JClassRegistry;
import org.jackie.compilerimpl.typeregistry.MultiRegistry;
import org.jackie.compilerimpl.filemanager.JarFileManager;
import org.jackie.compilerimpl.filemanager.InMemoryFileManager;
import org.jackie.compilerimpl.filemanager.ClassPathFileManager;
import org.jackie.compilerimpl.jmodelimpl.JClassImpl;
import org.jackie.compilerimpl.jmodelimpl.FlagsHelper;
import org.jackie.compilerimpl.ext.ExtensionManagerImpl;
import org.jackie.test.jclassfile.CodeSamples;
import org.jackie.test.jclassfile.Util;
import org.jackie.test.java5.annotation.TestCase;
import org.jackie.test.jvm.Samples.Bug;
import org.jackie.test.jvm.Samples.Annotated;
import org.jackie.jvm.JClass;
import org.jackie.jclassfile.model.ClassFile;
import org.jackie.jclassfile.model.FieldInfo;
import org.jackie.jclassfile.model.MethodInfo;
import org.jackie.jclassfile.flags.Access;
import org.jackie.jclassfile.flags.Flags;
import org.jackie.jclassfile.attribute.std.ConstantValue;
import org.jackie.utils.Assert;
import org.jackie.utils.ByteArrayDataInput;
import static org.jackie.utils.Assert.*;
import org.jackie.context.Context;
import org.jackie.context.ContextManager;
import static org.jackie.context.ContextManager.*;
import org.jackie.tools.DumpClass;

import java.io.File;
import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;

/**
 * @author Patrik Beno
 */
public class JClassTest extends TestCase {

	@Test
	public void checkClassFlags() {
		run(new Runnable() {
			public void run() {
				Class cls;

				cls = Samples.Clazz.class;
				expected(javac(cls).flags(), jackie(cls).flags(), "flags? static public %s", cls);

				cls = Samples.Interface.class;
				expected(javac(cls).flags(), jackie(cls).flags(), "flags? static %s", cls);
			}
		});
	}

	@Test
	public void checkFieldsBasic() {
		run(new Runnable() {
			public void run() {
				Class cls = Samples.Fields.class;
				ClassFile javac = javac(cls);
				ClassFile jackie = jackie(cls);
				expected(javac.flags(), jackie.flags(), "flags? %s", cls);
				for (FieldInfo f : javac.fields()) {
					FieldInfo f2 = jackie.getField(f.name());
					expected(f.name(), f2.name(), "name? %s", f);
					expected(f.descriptor(), f2.descriptor(), "descriptor? %s", f);
				}
			}
		});
	}

	@Test
	public void checkMethodsBasic() {
		run(new Runnable() {
			public void run() {
				Class cls = Samples.Methods.class;
				ClassFile javac = javac(cls);
				ClassFile jackie = jackie(cls);
				expected(javac.flags(), jackie.flags(), "flags? %s", cls);
				for (MethodInfo m : javac.methods()) {
					MethodInfo m2 = jackie.getMethod(m.name(), m.descriptor());
					NOTNULL(m2, "missing? %s", m);
					expected(m.descriptor(), m2.descriptor(), "descriptor? %s", m);
				}
			}
		});
	}

	@Test
	public void bug() {
		run(new Runnable() {
			public void run() {
				Class cls = JavacTool.class;
				ClassFile javac = javac(cls);
				ClassFile jackie = jackie(cls);

				try {
					DumpClass.dump(new ByteArrayInputStream(Util.getByteCode(cls)), new PrintStream("by-javac.txt"));
					Util.save(javac.toByteArray(), "by-javac-rewritten.class");
					DumpClass.dump(new ByteArrayInputStream(javac.toByteArray()), new PrintStream("by-javac-rewritten.txt"));

//					ClassFile cf = new ClassFile();
//					cf.load(new ByteArrayDataInput(javac.toByteArray()));
//					DumpClass.dump(new ByteArrayInputStream(cf.toByteArray()), new PrintStream("by-javac-rewritten-twice.txt"));
//					DumpClass.dump(new ByteArrayInputStream(jackie.toByteArray()), new PrintStream("by-jackie.txt"));
				} catch (FileNotFoundException e) {
					throw Assert.notYetHandled(e);
				}

//				expected(javac.attributes(), jackie.attributes(), "attributes? %s", cls);
			}
		});
	}

	@Test
	public void bug2() {
		run(new Runnable() {
			public void run() {
				Class cls = Samples.Annotated.class;
				ClassFile javac = javac(cls);

				try {
					Util.save(Util.getByteCode(cls), "by-javac.class");
					Util.save(javac.toByteArray(), "by-javac-rewritten.class");

					DumpClass.dump(new ByteArrayInputStream(Util.getByteCode(cls)), new PrintStream("by-javac.txt"));
					DumpClass.dump(new ByteArrayInputStream(javac.toByteArray()), new PrintStream("by-javac-rewritten.txt"));
				} catch (FileNotFoundException e) {
					throw Assert.notYetHandled(e);
				}

//				expected(javac.attributes(), jackie.attributes(), "attributes? %s", cls);
			}
		});
	}

}
