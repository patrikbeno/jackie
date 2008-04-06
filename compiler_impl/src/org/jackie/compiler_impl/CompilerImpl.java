package org.jackie.compiler_impl;

import org.jackie.compiler.Compiler;
import org.jackie.compiler.typeregistry.TypeRegistry;
import org.jackie.compiler.context.CompilerContext;
import org.jackie.compiler.filemanager.FileManager;
import org.jackie.compiler.filemanager.FileObject;
import org.jackie.compiler_impl.filemanager.MultiFileManager;
import org.jackie.compiler_impl.javacintegration.JavacCompiler;
import org.jackie.compiler_impl.bytecode.BCClassContext;
import org.jackie.compiler_impl.bytecode.Compilable;
import static org.jackie.context.ContextManager.context;
import static org.jackie.context.ContextManager.newContext;
import static org.jackie.context.ContextManager.closeContext;
import org.jackie.jvm.JClass;
import static org.jackie.utils.Assert.typecast;
import org.jackie.utils.ClassName;
import org.jackie.utils.IOHelper;
import org.objectweb.asm.ClassWriter;

import java.util.Arrays;
import java.util.List;
import java.nio.ByteBuffer;

/**
 * @author Patrik Beno
 */
public class CompilerImpl implements Compiler {

	protected FileManager sources;

	protected FileManager workspace;

	protected List<FileManager> dependencies;

	public void compile() {
		compileJavaSources();
		compileByteCode();
	}

	void compileJavaSources() {
		List<String> options = Arrays.asList("-g"/*, "-source", "1.5", "-target", "1.5"*/);
		JavacCompiler javac = new JavacCompiler(options,
				sources, new MultiFileManager(dependencies), workspace);
		javac.compile();
	}

	void compileByteCode() {
		TypeRegistry tr = context(CompilerContext.class).workspace.typeRegistry;
		for (JClass jcls : tr.jclasses()) {
			compile(jcls);
		}
	}

	void compile(JClass jcls) {
		newContext();
		try {
			context().set(BCClassContext.class, new BCClassContext());

			ClassWriter cw = new ClassWriter(0);
			context(BCClassContext.class).classVisitor = cw;

			// compilation:
			typecast(jcls, Compilable.class).compile();

			ClassName clsname = new ClassName(jcls.getFQName());
			FileObject fo = workspace.create(clsname.getPathName());

			IOHelper.write(
					ByteBuffer.wrap(cw.toByteArray()),
					fo.getOutputChannel());


		} finally {
			closeContext();
		}

	}


}
