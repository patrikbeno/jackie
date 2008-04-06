package org.jackie.compiler_impl;

import org.jackie.compiler.Compiler;
import org.jackie.compiler.filemanager.FileManager;
import org.jackie.compiler_impl.filemanager.MultiFileManager;
import org.jackie.compiler_impl.javacintegration.JavacCompiler;
import org.jackie.utils.Assert;

import java.util.Arrays;
import java.util.List;

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

	private void compileJavaSources() {
		List<String> options = Arrays.asList("-g"/*, "-source", "1.5", "-target", "1.5"*/);
		JavacCompiler javac = new JavacCompiler(options,
				sources, new MultiFileManager(dependencies), workspace);
		javac.compile();
	}

	private void compileByteCode() {
		throw Assert.notYetImplemented(); // todo implement this
	}


}
