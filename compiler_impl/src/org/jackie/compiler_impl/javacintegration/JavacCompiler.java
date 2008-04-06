package org.jackie.compiler_impl.javacintegration;

import org.jackie.compiler.filemanager.FileManager;
import org.jackie.utils.Assert;

import com.sun.source.util.JavacTask;
import com.sun.tools.javac.api.JavacTool;

import javax.tools.JavaFileObject;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class JavacCompiler {

	protected FileManager sources;

	protected FileManager classpath;

	protected FileManager output;
	
	protected List<String> options;

	public JavacCompiler(List<String> options, FileManager sources, FileManager classpath, FileManager output) {
		this.sources = sources;
		this.classpath = classpath;
		this.output = output;
	}

	public void compile() {
		JavacTool javac = JavacTool.create();
		JFileManager jfm = new JFileManager(sources, classpath, output);
		Writer out = new PrintWriter(System.out);
		List<JavaFileObject> units = jfm.getJavaSourceFiles();
		JavacTask task = javac.getTask(out, jfm, new JavacDiagnosticListener(), options, null, units);
		Boolean success = task.call();
		Assert.doAssert(success, "Compilation failure!");
	}


}
