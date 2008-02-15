package org.jackie.compiler.filemanager;

import org.jackie.utils.Assert;

import org.testng.annotations.Test;

import java.io.File;

/**
 * @author Patrik Beno
 */
@Test
public class JarEntryFileObjectTest {

	public void read() throws Exception {
		String jhome = System.getProperty("java.home");
		String baseurl = new File(jhome, "lib/rt.jar").toURI().toURL().toExternalForm();
		JarEntryFileObject je = new JarEntryFileObject(baseurl, "java/lang/Object.class");

		Assert.notNull(je.getInputChannel());
		Assert.doAssert(je.getSize()>0, "size=%s", je.getSize());
		Assert.doAssert(je.getLastModified()>0, "lastModified=%s", je.getLastModified());
	}

}
