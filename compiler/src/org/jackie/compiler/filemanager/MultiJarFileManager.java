package org.jackie.compiler.filemanager;

import org.jackie.utils.Assert;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public class MultiJarFileManager implements FileManager {

	protected List<JarFileManager> managers = new ArrayList<JarFileManager>();

	public MultiJarFileManager(List<URL> urls) {
		for (URL url : urls) {
			managers.add(new JarFileManager(url));
		}
	}

	public Set<String> getPathNames() {
		Set<String> all = new HashSet<String>();
		for (JarFileManager fm : managers) {
			all.addAll(fm.getPathNames());
		}
		return all;
	}

	public FileObject getFileObject(String pathname) {
		FileObject fo = null;
		for (JarFileManager fm : managers) {
			fo = fm.getFileObject(pathname);
			if (fo != null) {
				break;
			}
		}
		return fo;
	}

	public FileObject create(String pathname) {
		throw Assert.notYetImplemented(); // todo implement this
	}
}
