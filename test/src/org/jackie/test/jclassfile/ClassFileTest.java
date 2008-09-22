package org.jackie.test.jclassfile;

import org.jackie.jclassfile.model.ClassFile;
import org.jackie.jclassfile.model.FieldInfo;
import org.jackie.jclassfile.model.Type;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.util.TypeDescriptor;
import org.jackie.jclassfile.util.Helper;
import org.jackie.jclassfile.flags.Access;
import org.jackie.jclassfile.attribute.GenericAttribute;
import org.jackie.utils.Assert;
import static org.jackie.utils.Assert.expected;
import org.jackie.jvm.spi.JModelHelper;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

/**
 * @author Patrik Beno
 */
public class ClassFileTest {

	@Test
	public void loadAndSave() throws Exception {
		byte[] original = Sample.bytecode();

		ClassFile cf = new ClassFile();
		cf.load(new DataInputStream(new ByteArrayInputStream(original)));

		byte[] saved = cf.toByteArray();

		expected(true, Arrays.equals(original, saved), "original != saved");
	}

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

	@Test
	public void generateComplexClass() {
		final ClassFile cf = new ClassFile();
		cf.classname("test/Simple").superclass("java/lang/Object");
		cf.addInterface("java/io/Serializable");

		FieldInfo f = new FieldInfo(cf);
		f.name("sample");
		f.typeDescriptor(new TypeDescriptor(Type.CLASS, 0, "java/lang/String"));
		f.flags().set(Access.PROTECTED);
		cf.addField(f);

		GenericAttribute a = new GenericAttribute(f, cf.pool().factory().getUtf8("AnyAttribute"));
		a.data(new byte[1024]);
		f.addAttribute(a);

		final byte[] bytes = cf.toByteArray();

		new ClassLoader(null) {{
				defineClass("test.Simple", bytes, 0, bytes.length);
		}};
	}
}

