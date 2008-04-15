package org.jackie.compiler_impl.filemanager;

import org.jackie.compiler.filemanager.FileObject;
import static org.jackie.utils.Assert.doAssert;
import org.jackie.utils.Log;
import org.jackie.utils.TimedTask;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Set;

/**
 * @author Patrik Beno
 */
@Test
public class FileManagerTest {

	public void loadJar() throws Exception {

      TimedTask loading = new TimedTask();
      loading.start();

      String jhome = System.getProperty("java.home");
      File f = new File(jhome, "lib/rt.jar");
      doAssert(f.exists(), "Cannot load rt.jar");

      JarFileManager fm = new JarFileManager(f);
      Set<String> pathnames = fm.getPathNames();
      doAssert(!pathnames.isEmpty(), "Failed to load path names.");

      loading.stop();

      Log.debug("Loaded %s pathnames (%s bytes) in %s msec (%s usec/entry)",
					 pathnames.size(), countBytes(fm), loading.duration(), loading.duration()*1000/pathnames.size());
   }

   @Test(groups = {"perf"}, invocationCount = 5)
   public void perftest() throws Exception {
      loadJar();
   }

   private long countBytes(JarFileManager fm) {
      long size = 0;
      for (FileObject fo : fm.getFileObjects()) {
         size += fo.getSize();
      }
      return size;
   }

	public void classpath() {
		ClassPathFileManager fm = new ClassPathFileManager(true, true);
		fm.getPathNames();
		fm.getFileObject(Object.class.getName());

		long size=0;
		for (FileObject fo : fm.getFileObjects()) {
			size+=fo.getSize();
		}

		Log.debug("Available %s FileObjects (%s bytes)", fm.getPathNames().size(), size);
	}
}
