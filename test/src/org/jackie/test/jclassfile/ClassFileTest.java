package org.jackie.test.jclassfile;

import org.jackie.jclassfile.model.ClassFile;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * @author Patrik Beno
 */
public class ClassFileTest {

	@Test
	public void generateSimpleClass() throws IOException {
		final ClassFile cf = new ClassFile();
		cf.classname("test/Simple").superclass("java/lang/Object");

		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		cf.save(new DataOutputStream(baos));
		final byte[] bytes = baos.toByteArray();

		FileOutputStream out = new FileOutputStream("h:/var/out.class");
		out.write(bytes);
		out.close();

		new ClassLoader(null) {{
			Class<?> cls = defineClass(cf.classname().replace('/','.'), bytes, 0, bytes.length);
			System.out.println(cls);
		}};

	}


	@Test
	public void load() throws IOException {
//		final Class cls = MessageFormat.class;
		final Class cls = Sample.class;

		URL url = cls.getResource(cls.getSimpleName() + ".class");
		byte[] original = new byte[url.openConnection().getContentLength()];

		{
			DataInputStream in = new DataInputStream(url.openStream());
			in.readFully(original);
		}

		{
			FileOutputStream out = new FileOutputStream("h:/var/original.class");
			out.write(original);
			out.close();
		}

		ClassFile classfile = new ClassFile();
		classfile.load(new DataInputStream(new ByteArrayInputStream(original)));

		final byte[] bytes;
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(baos);
			classfile.save(out);
			out.close();
			bytes = baos.toByteArray();
		}

		{
			FileOutputStream out = new FileOutputStream("h:/var/out.class");
			out.write(bytes);
			out.close();
		}

		new ClassLoader(null) {{
				defineClass(cls.getName(), bytes, 0, bytes.length);
		}};
	}
}

