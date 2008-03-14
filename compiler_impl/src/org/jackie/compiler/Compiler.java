package org.jackie.compiler;

import org.jackie.compiler.filemanager.FileManager;
import org.jackie.compiler.filemanager.MultiFileManager;
import org.jackie.compiler.javacintegration.JavacCompiler;
import org.jackie.utils.Assert;

import java.util.List;

/**
 * @author Patrik Beno
 */
public class Compiler {

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
