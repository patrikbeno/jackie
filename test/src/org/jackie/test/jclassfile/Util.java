package org.jackie.test.jclassfile;

import static org.jackie.utils.Assert.NOTNULL;
import org.jackie.utils.Assert;
import org.jackie.utils.ByteArrayDataInput;
import org.jackie.jclassfile.model.ClassFile;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Patrik Beno
 */
public class Util {

	static public byte[] getByteCode(Class cls) {
		try {
			String sname = cls.getName();
			sname = sname.substring(sname.lastIndexOf('.')+1);
			URL url = NOTNULL(cls.getResource(sname + ".class"));
			URLConnection con = url.openConnection();
			byte[] bytes = new byte[con.getContentLength()];
			DataInputStream in = new DataInputStream(con.getInputStream());
			in.readFully(bytes);
			return bytes;
		} catch (IOException e) {
			throw Assert.unexpected(e);
		}
	}

	static public void validate(String clsname, byte[] bytes) {
		boolean ok = true;
		try {
			parseClassFile(bytes);
		} catch (Exception e) {
			e.printStackTrace();
			ok = false;
		}
		try {
			loadInClassLoader(clsname, bytes);
		} catch (Exception e) {
			e.printStackTrace();
			ok = false;
		}
		Assert.expected(true, ok, "not ok! (%s)", clsname);
	}

	static public ClassFile parseClassFile(final byte[] bytes) {
		try {
			ClassFile cf = new ClassFile();
			cf.load(new ByteArrayDataInput(bytes));
			return cf;
		} catch (Exception e) {
			throw Assert.assertFailed(e);
		}
	}

	static public void loadInClassLoader(final String name, final byte[] bytes) {
		new ClassLoader(Thread.currentThread().getContextClassLoader()) {{
			try {
				Class<?> cls = defineClass(name, bytes, 0, bytes.length);
				cls.getDeclaredFields();
				cls.getDeclaredMethods();
				cls.getConstructors();
			} catch (Exception e) {
				throw Assert.assertFailed(e);
			}
		}};
	}

}
