package org.jackie.compiler_impl.filemanager;

import org.jackie.compiler_impl.filemanager.foimpl.URLFileObject;
import org.jackie.compiler.filemanager.FileManager;
import org.jackie.compiler.filemanager.FileObject;

import java.net.URL;
import java.util.Collections;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public abstract class URLFileManager extends AbstractFileManager implements FileManager {

	protected URL base;
	protected Set<String> pathnames;

	public URLFileManager(URL base) {
		this.base = base;
	}

	abstract protected Set<String> populatePathNames();

	public Set<String> getPathNames() {
		checkInitPathNames();
		return Collections.unmodifiableSet(pathnames);
	}

	public FileObject getFileObject(String pathname) {
		checkInitPathNames();
		if (!pathnames.contains(pathname)) {
			return null;
		}
		return new URLFileObject(base, pathname);
	}

	protected void checkInitPathNames() {
		if (pathnames == null) {
			pathnames = populatePathNames();
		}
	}

}
