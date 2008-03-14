package org.jackie.compiler.javacintegration;

import org.jackie.compiler.filemanager.FileManager;
import org.jackie.compiler.filemanager.FileObject;
import org.jackie.compiler.filemanager.InMemoryFileManager;
import org.jackie.compiler.filemanager.JarFileManager;
import org.jackie.compiler.filemanager.foimpl.SourceFileObject;
import org.jackie.utils.Assert;
import org.jackie.utils.Log;
import org.jackie.utils.TimedTask;

import org.testng.annotations.Test;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public class JavacCompilerTest {

   @Test
   public void compile() throws Exception {

      String jhome = System.getProperty("java.home");
      File f = new File(jhome, "lib/rt.jar");
      Assert.doAssert(f.exists(), "Cannot find rt.jar");

      InMemoryFileManager sources = new InMemoryFileManager();
      JarFileManager classpath = new JarFileManager(f);
      InMemoryFileManager output = new InMemoryFileManager();

      TestSources.load(sources);

      TimedTask compilation = new TimedTask().start();

      JavacCompiler compiler = new JavacCompiler(
            sources,
            classpath,
            output
      );
      compiler.compile();

      Set<String> pathnames = output.getPathNames();
      long size = 0;
      for (String pathname : pathnames) {
			FileObject fo = compiler.output.getFileObject(pathname);
			size += fo.getSize();
		}

      compilation.stop();

      Log.debug("Compiled %s classes (total size: %s) in %s msec", pathnames.size(), size, compilation.duration());

   }

   @Test(groups = {"perf"}, invocationCount = 5)
   public void perftest() throws Exception {
      compile();
   }

   static class ClassPathFileManager implements FileManager {

      Set<String> clsnames = ClassList.read();
      Set<String> pathnames;

      public Set<String> getPathNames() {
         if (pathnames != null) {
            return pathnames;
         }
         Set<String> pathnames = new HashSet<String>();
         for (String clsname : clsnames) {
            String pathname = clsname.replace(".", "/") + ".class";
            pathnames.add(pathname);
         }
         return (this.pathnames = pathnames);
      }

      public FileObject getFileObject(String pathname) {
         return new SourceFileObject(pathname, Thread.currentThread().getContextClassLoader().getResource(pathname));
      }

      public FileObject create(String pathname) {
         throw Assert.unsupported();
      }

		public Iterable<FileObject> getFileObjects() {
			throw Assert.unsupported(); 
		}
	}

}
