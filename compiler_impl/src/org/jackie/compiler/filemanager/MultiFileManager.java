package org.jackie.compiler.filemanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public class MultiFileManager extends AbstractFileManager implements FileManager {

	protected List<FileManager> managers;

	public MultiFileManager(FileManager... managers) {
		this(Arrays.asList(managers));
	}

	public MultiFileManager(List<FileManager> managers) {
		this.managers = new ArrayList<FileManager>(managers);
	}

	public Set<String> getPathNames() {
		Set<String> all = new HashSet<String>();
		for (FileManager fm : managers) {
			all.addAll(fm.getPathNames());  
		}
		return all;
	}

	public FileObject getFileObject(String pathname) {
		FileObject fo = null;
		for (FileManager fm : managers) {
			fo = fm.getFileObject(pathname);
			if (fo != null) {
				break;
			}
		}
		return fo;
	}

}
