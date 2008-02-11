package org.jackie.compiler.filemanager;

import org.testng.annotations.Test;
import org.jackie.utils.Log;
import org.jackie.utils.Assert;
import org.jackie.utils.TimedTask;
import static org.jackie.utils.Assert.doAssert;

import java.io.File;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public class FileManagerTest {

   @Test
   public void loadJar() throws Exception {

      TimedTask loading = new TimedTask();
      loading.start();

      String jhome = System.getProperty("java.home");
      File f = new File(jhome, "lib/rt.jar");
      doAssert(f.exists(), "Cannot load rt.jar");

      JarFileManager fm = new JarFileManager(f.toURI().toURL());
      Set<String> pathnames = fm.getPathNames();
      doAssert(!pathnames.isEmpty(), "Failed to load path names.");

      loading.stop();

      Log.debug("Loaded %s pathnames (%s bytes) in %s msec", pathnames.size(), countBytes(fm), loading.duration());
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

}
