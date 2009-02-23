package org.jackie.compilerimpl.filemanager;

import java.io.File;

/**
 * @author Patrik Beno
 */
public class JavaRuntimeFileManager extends FilteredJarFileManager {

	public JavaRuntimeFileManager() {
		this(new File(System.getProperty("java.home")));
	}

	public JavaRuntimeFileManager(File jrehome) {
		super(new File(jrehome, "lib/rt.jar"),
				// warning: temporary solution to allow javac sources compilation (conflict with classes already in rt.jar)
				"com.sun.(source|tools.javac).*",
				"javax.(annotation.processing|lang.model|tools).*");
	}
}
