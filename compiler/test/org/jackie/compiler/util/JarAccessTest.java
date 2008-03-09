package org.jackie.compiler.util;

import org.jackie.utils.Assert;
import static org.jackie.utils.Assert.doAssert;
import org.jackie.utils.Log;
import org.jackie.utils.TimedTask;

import org.testng.annotations.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * @author Patrik Beno
 */
@Test
public class JarAccessTest {

	@Test(groups={"perf"}, invocationCount=5)
	public void randomAccesJarEntries() throws Exception {
		// detect rt.jar path
		String jhome = System.getProperty("java.home");
		File f = new File(jhome, "lib/rt.jar");
		doAssert(f.exists(), "Cannot load rt.jar");

		// load supported pathnames
		List<String> pathnames = new ArrayList<String>();
		JarInputStream jin = new JarInputStream(new BufferedInputStream(new FileInputStream(f)));
		for (JarEntry je; (je = jin.getNextJarEntry()) != null;) {
			pathnames.add(je.getName());
		}
		Collections.shuffle(pathnames);

		TimedTask loading = new TimedTask();
		loading.start();

		int total = 0;

		for (String pathname : pathnames) {
			URL url = new URL("jar:"+f.toURI().toURL()+"!/"+pathname);
			int read = read(url.openStream());
			Assert.doAssert(read>0, "read=%s: %s", read, url);
			total += read;
		}

		loading.stop();
		
		Log.debug("rt.jar loaded (%s entries, %s bytes) in %s msec (%s usec/entry)",
					 pathnames.size(), total, loading.duration(), loading.duration()*1000/pathnames.size());
	}

	byte[] buf = new byte[1024*1024];

	int read(InputStream in) throws Exception {
		int total=0;
		for (int read=0; read!=-1; total+=read) {
			read = in.read(buf);
		}
		return total;
	}

}
