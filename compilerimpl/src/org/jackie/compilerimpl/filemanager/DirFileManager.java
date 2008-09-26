package org.jackie.compilerimpl.filemanager;

import org.jackie.utils.Assert;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public class DirFileManager extends URLFileManager {

	protected File directory;

	public DirFileManager(File directory) {
		super(getBaseURL(directory));
		this.directory = directory;
	}

	protected Set<String> populatePathNames() {
		final Set<String> names = new HashSet<String>();
		directory.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				if (pathname.isFile()) {
					names.add(toPathName(pathname));
				} else {
					pathname.listFiles(this); // recurse into directory
				}
				return false;
			}
		});
		return names;
	}

	private String toPathName(File pathname) {
		try {
			return base.toURI().relativize(pathname.toURI()).getPath();
		} catch (URISyntaxException e) {
			throw Assert.notYetHandled(e);
		}
	}

	static private URL getBaseURL(File dir) {
		try {
			return dir.toURI().toURL();
		} catch (MalformedURLException e) {
			throw Assert.notYetHandled(e);
		}
	}
}
