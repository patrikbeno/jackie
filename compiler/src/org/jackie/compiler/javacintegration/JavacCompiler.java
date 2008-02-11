package org.jackie.compiler.javacintegration;

import com.sun.tools.javac.api.JavacTool;
import com.sun.source.util.JavacTask;
import javax.tools.JavaFileObject;

import org.jackie.compiler.filemanager.FileManager;
import org.jackie.utils.Assert;

import java.io.Writer;
import java.io.PrintWriter;
import java.util.List;
import java.util.Arrays;

/**
 * @author Patrik Beno
 */
public class JavacCompiler {

	protected FileManager sources;

	protected FileManager classpath;

	protected FileManager output;

	public JavacCompiler(FileManager sources, FileManager classpath, FileManager output) {
		this.sources = sources;
		this.classpath = classpath;
		this.output = output;
	}

	public void compile() {
		JavacTool javac = JavacTool.create();
		JFileManager jfm = new JFileManager(sources, classpath, output);
		Writer out = new PrintWriter(System.out);
		List<String> options = Arrays.asList("-g"/*, "-source", "1.5", "-target", "1.5"*/);
		List<JavaFileObject> units = jfm.getJavaSourceFiles();
		JavacTask task = javac.getTask(out, jfm, new JavacDiagnosticListener(), options, null, units);
		Boolean success = task.call();
		Assert.doAssert(success, "Compilation failure!");
	}


}
