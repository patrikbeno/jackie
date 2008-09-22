package org.jackie.test.jclassfile;

import static org.jackie.utils.Assert.NOTNULL;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Patrik Beno
 */
public class Sample {

	static public byte[] bytecode() throws IOException {
		URL url = NOTNULL(Sample.class.getResource(Sample.class.getSimpleName() + ".class"));
		URLConnection con = url.openConnection();
		byte[] bytes = new byte[con.getContentLength()];
		DataInputStream in = new DataInputStream(con.getInputStream());
		in.readFully(bytes);
		return bytes;
	}

}
