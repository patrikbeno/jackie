package org.jackie.compilerimpl.filemanager;

import org.jackie.utils.Assert;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * @author Patrik Beno
 */
public class JarFileManager extends URLFileManager {

	protected File jarfile;

	public JarFileManager(File jarfile) {
		super(getBaseURL(jarfile));
		this.jarfile = jarfile;
	}

	protected Set<String> populatePathNames() {
		try {
			Set<String> entries = new HashSet<String>();
			JarInputStream jin = new JarInputStream(new BufferedInputStream(new FileInputStream(jarfile)));
			for (JarEntry je; (je = jin.getNextJarEntry()) != null;) {
				entries.add(je.getName());
			}
			return entries;
		} catch (IOException e) {
			throw Assert.notYetHandled(e);
		}
	}

	static private URL getBaseURL(File f) {
		try {
			return new URL("jar:"+f.toURI()+"!/");
		} catch (MalformedURLException e) {
			throw Assert.notYetHandled(e);
		}
	}

}
