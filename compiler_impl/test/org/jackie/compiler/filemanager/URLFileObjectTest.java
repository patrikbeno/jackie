package org.jackie.compiler.filemanager;

import org.jackie.utils.Assert;
import org.jackie.compiler.filemanager.foimpl.URLFileObject;

import org.testng.annotations.Test;

import java.io.File;
import java.net.URI;
import java.net.URL;

/**
 * @author Patrik Beno
 */
@Test
public class URLFileObjectTest {

	public void read() throws Exception {
		String jhome = System.getProperty("java.home");
		URI jaruri = new File(jhome, "lib/rt.jar").toURI();
		URL base = new URL("jar:"+jaruri+"!/");
		URLFileObject je = new URLFileObject(base, "java/lang/Object.class");

		Assert.notNull(je.getInputChannel());
		Assert.doAssert(je.getSize()>0, "size=%s", je.getSize());
		Assert.doAssert(je.getLastModified()>0, "lastModified=%s", je.getLastModified());
	}

}
