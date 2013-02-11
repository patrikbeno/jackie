package org.jackie.compilerimpl.javacintegration;

import com.sun.source.util.JavacTask;
import com.sun.tools.javac.api.JavacTool;
import org.jackie.compiler.filemanager.FileManager;
import org.jackie.utils.Assert;

import javax.tools.JavaFileObject;
import java.io.PrintWriter;
import java.io.Writer;
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
		this.options = options;
		this.sources = sources;
		this.classpath = classpath;
		this.output = output;
	}

	public FileManager getSources() {
		return sources;
	}

	public FileManager getClasspath() {
		return classpath;
	}

	public FileManager getOutput() {
		return output;
	}

	public List<String> getOptions() {
		return options;
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
