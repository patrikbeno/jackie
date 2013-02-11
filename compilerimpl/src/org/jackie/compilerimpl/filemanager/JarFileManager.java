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
import org.jackie.utils.IOHelper;

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
		FileInputStream in = null;
		try {
			Set<String> entries = new HashSet<String>();
			in = new FileInputStream(jarfile);
			JarInputStream jin = new JarInputStream(new BufferedInputStream(in));
			for (JarEntry je; (je = jin.getNextJarEntry()) != null;) {
				entries.add(je.getName());
			}
			return entries;
		} catch (IOException e) {
			throw Assert.notYetHandled(e);
		} finally {
			IOHelper.close(in);
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
