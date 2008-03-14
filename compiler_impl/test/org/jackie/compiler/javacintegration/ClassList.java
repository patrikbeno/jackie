package org.jackie.compiler.javacintegration;

import org.jackie.utils.Assert;
import static org.jackie.utils.Assert.doAssert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * @author Patrik Beno
 */
public class ClassList {

   static Set<String> read() {
      try {
         String jhome = System.getProperty("java.home");
         File f = new File(jhome, "lib/rt.jar");
         doAssert(f.exists(), "Cannot load rt.jar");

         Set<String> result = new HashSet<String>();

         JarInputStream jin = new JarInputStream(new FileInputStream(f));
         JarEntry je = null;
         while ((je = jin.getNextJarEntry()) != null) {
            result.add(je.getName());
         }

         return result;
      } catch (IOException e) {
         throw Assert.assertFailed(e);
      }
   }

}
