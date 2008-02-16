package org.jackie.compiler.filemanager;

import org.jackie.utils.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public class ClassPathFileManager extends MultiFileManager {

	public ClassPathFileManager() {
		this(true, true);
	}

	public ClassPathFileManager(boolean useBootClassPath, boolean useClasspath, String ... classpath) {
		super(buildClassPath(useBootClassPath, useClasspath, classpath));
	}

	static private List<FileManager> buildClassPath(boolean useBootClassPath, boolean useClasspath, String[] classpath) {
		Set<File> accepted = new HashSet<File>();
		List<File> sorted = new ArrayList<File>();

		if (useBootClassPath) {
			gatherPathNames(sorted, accepted, split(System.getProperty("sun.boot.class.path")));
		}
		if (useClasspath) {
			gatherPathNames(sorted, accepted, split(System.getProperty("java.class.path")));
		}
		gatherPathNames(sorted, accepted, classpath);

		return createFileManagers(sorted);
	}

	static private String[] split(String classpath) {
		return classpath.split(File.pathSeparator);
	}

	static private void gatherPathNames(List<File> sorted, Set<File> accepted, String[] names) {
		if (names == null) { return; }
		for (String s : names) {
			File f = new File(s);
			if (accepted.contains(f)) {
				Log.warn("Ignoring duplicate pathname: %s", f);
				continue;
			}
			sorted.add(f);
			accepted.add(f);
		}
	}

	static private List<FileManager> createFileManagers(List<File> sorted) {
		List<FileManager> managers = new ArrayList<FileManager>();
		for (File f : sorted) {
			URLFileManager fm = (f.isFile()) ? new JarFileManager(f) : new DirFileManager(f);
			managers.add(fm);
			Log.debug("Created file manager for %s", f);
		}
		return managers;
	}


}
