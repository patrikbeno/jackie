package org.jackie.compiler.filemanager;

import org.jackie.compiler.filemanager.FileManager;
import org.jackie.compiler.filemanager.FileObject;

import java.util.Set;

/**
 * @author Patrik Beno
 */
public abstract class FileManagerDelegate implements FileManager {

	FileManager fm;

	public FileManagerDelegate(FileManager fm) {
		this.fm = fm;
	}

	public Set<String> getPathNames() {
		return fm.getPathNames();
	}

	public FileObject getFileObject(String pathname) {
		return fm.getFileObject(pathname);
	}

	public FileObject create(String pathname) {
		return fm.create(pathname);
	}

	public void remove(String pathname) {
		fm.remove(pathname);
	}

	public Iterable<FileObject> getFileObjects() {
		return fm.getFileObjects();
	}
}
