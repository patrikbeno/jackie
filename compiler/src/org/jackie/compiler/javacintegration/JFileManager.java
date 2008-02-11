package org.jackie.compiler.javacintegration;

import org.jackie.compiler.filemanager.FileManager;
import org.jackie.utils.Assert;

import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import javax.tools.JavaFileObject.Kind;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * @author Patrik Beno
 */
public class JFileManager implements JavaFileManager {

	protected FileManager sources;

	protected FileManager classpath;

	protected FileManager output;

	///


	public JFileManager(FileManager sources, FileManager classpath, FileManager output) {
		this.sources = sources;
		this.classpath = classpath;
		this.output = output;

	}

	/// JavaFileManager ///


	public ClassLoader getClassLoader(Location location) {
		return Thread.currentThread().getContextClassLoader();
	}

	public Iterable<JavaFileObject> list(Location location, String packageName,
													 Set<JavaFileObject.Kind> kinds,
													 boolean recurse) throws IOException {

		Assert.doAssert(kinds.size() == 1, "Too many kinds (expecting single one): %s", kinds);

		Set<JavaFileObject> selected = new HashSet<JavaFileObject>();
		String prefix = packageName.replace(".", "/") + "/";
		Kind kind = kinds.iterator().next();
		FileManager fm = selectFileManager(kind);

		for (String pathname : fm.getPathNames()) {
			boolean matches = true;
			matches &= pathname.startsWith(prefix) && pathname.endsWith(kind.extension);
			matches &= recurse || pathname.indexOf('/', prefix.length() + 1) == -1;
			if (matches) {
				JFO jfo = new JFO(fm.getFileObject(pathname), kind);
				selected.add(jfo);
			}
		}

		return selected;
	}

	public String inferBinaryName(Location location, JavaFileObject file) {
		JFO jfo = Assert.typecast(file, JFO.class);
		String pathname = jfo.fobject.getPathName();
		String name = pathname.replace(file.getKind().extension, "").replace("/", ".");
		return name;
	}

	public boolean isSameFile(FileObject a, FileObject b) {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public boolean handleOption(String current, Iterator<String> remaining) {
		return false;
	}

	public boolean hasLocation(Location location) {
		return true;
	}

	public JavaFileObject getJavaFileForInput(Location location, String className,
															Kind kind) throws IOException {
		return getClassJFO(className, kind);
	}

	public JavaFileObject getJavaFileForOutput(Location location, String className, Kind kind,
															 FileObject sibling) throws IOException {
		if (location.equals(StandardLocation.CLASS_OUTPUT)) {
			return new JFO(output.create(className.replace('.', '/') + kind.extension), kind);
		} else {
			return getClassJFO(className, kind);
		}
	}

	public FileObject getFileForInput(Location location, String packageName,
												 String relativeName) throws IOException {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public FileObject getFileForOutput(Location location, String packageName, String relativeName,
												  FileObject sibling) throws IOException {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public void flush() throws IOException {
	}

	public void close() throws IOException {
	}

	public int isSupportedOption(String option) {
		return -1;
	}

	protected JFO getClassJFO(String clsname, Kind kind) {
		FileManager fm = selectFileManager(kind);
		String pathname = toPathName(clsname, kind);
		org.jackie.compiler.filemanager.FileObject fobject = fm.getFileObject(pathname);
		JFO jfo = new JFO(fobject, kind);
		return jfo;
	}

	protected FileManager selectFileManager(Kind kind) {
		switch (kind) {
			case SOURCE:
				return sources;

			case CLASS:
				return classpath;

			default:
				throw Assert.invariantFailed(kind);
		}
	}

	protected String toPathName(String className, Kind kind) {
		return className.replace('.', '/') + kind.extension;
	}

	///


	public List<JavaFileObject> getJavaSourceFiles() {
		List<JavaFileObject> jfos = new ArrayList<JavaFileObject>();
		for (String name : sources.getPathNames()) {
			if (!name.endsWith(".java")) {
				continue;
			}

			org.jackie.compiler.filemanager.FileObject fobject = sources.getFileObject(name);
			JFO jfo = new JFO(fobject, Kind.SOURCE);
			jfos.add(jfo);
		}
		return jfos;
	}
}
