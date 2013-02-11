package org.jackie.compilerimpl.filemanager;

import org.jackie.compilerimpl.filemanager.foimpl.URLFileObject;
import org.jackie.compilerimpl.javacintegration.JFO;
import org.jackie.utils.Assert;
import org.junit.Test;

import javax.tools.JavaFileObject.Kind;
import java.io.File;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;

/**
 * @author Patrik Beno
 */
public class URLFileObjectTest {

    @Test
	public void read() throws Exception {
		String jhome = System.getProperty("java.home");
		URI jaruri = new File(jhome, "lib/rt.jar").toURI();
		URL base = new URL("jar:"+jaruri+"!/");
		URLFileObject je = new URLFileObject(base, "java/awt/Component.class");
		int read = je.getInputChannel().read(ByteBuffer.allocate((int) je.getSize()));

		Assert.notNull(je.getInputChannel());
		Assert.doAssert(je.getSize()>0, "size=%s", je.getSize());
		Assert.doAssert(je.getLastModified()>0, "lastModified=%s", je.getLastModified());
		Assert.expected((int) je.getSize(), read, "read?");

// this is bug from javac (see below)
//		byte[] bytes = readInputStream(new byte[]{}, new JFO(je, Kind.CLASS).openInputStream());
//		Assert.expected((int) je.getSize(), (int) bytes.length, "length?");


	}

	// [javac: com.sun.tools.javac.jvm.ClassReader.readInputStream] this loops forever when s.available()==0
	private static byte[] readInputStream(byte[] buf, InputStream s) throws IOException {
		 try {
			  buf = ensureCapacity(buf, s.available());
			  int r = s.read(buf);
			  int bp = 0;
			  while (r != -1) {
					bp += r;
					buf = ensureCapacity(buf, bp);
					r = s.read(buf, bp, buf.length - bp);
			  }
			  return buf;
		 } finally {
			  try {
					s.close();
			  } catch (IOException e) {
					/* Ignore any errors, as this stream may have already
					 * thrown a related exception which is the one that
					 * should be reported.
					 */
			  }
		 }
	}
	private static byte[] ensureCapacity(byte[] buf, int needed) {
		 if (buf.length < needed) {
			  byte[] old = buf;
			  buf = new byte[Integer.highestOneBit(needed) << 1];
			  System.arraycopy(old, 0, buf, 0, old.length);
		 }
		 return buf;
	}


}
