package org.jackie.compiler_impl;

import org.jackie.compiler.filemanager.FileManager;
import org.jackie.compiler_impl.filemanager.MultiFileManager;
import org.jackie.compiler_impl.javacintegration.JavacCompiler;
import org.jackie.utils.Assert;

import java.util.List;

/**
 * @author Patrik Beno
 */
public class CompilerImpl {

	protected FileManager sources;

	protected FileManager workspace;

	protected List<FileManager> dependencies;

	public void compile() {

		compileJavaSources();
		compileByteCode();
	}

	private void compileJavaSources() {
		JavacCompiler javac = new JavacCompiler(
				sources, new MultiFileManager(dependencies), workspace);
		javac.compile();
	}

	private void compileByteCode() {
		throw Assert.notYetImplemented(); // todo implement this
	}


}
