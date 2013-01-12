package org.jackie.test.jclassfile;

import org.jackie.jclassfile.attribute.GenericAttribute;
import org.jackie.jclassfile.flags.Access;
import org.jackie.jclassfile.model.ClassFile;
import org.jackie.jclassfile.model.FieldInfo;
import org.jackie.jclassfile.model.Type;
import org.jackie.jclassfile.util.TypeDescriptor;
import org.jackie.jclassfile.constantpool.impl.Utf8;
import org.objectweb.asm.ClassReader;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Patrik Beno
 */
public class ClassFileTest {

    @Test
	public void loadAndSaveSample() throws Exception {
		loadSaveValidate(Samples.class);
	}

    @Test
	public void loadAndSaveClassReader() throws Exception {
		loadSaveValidate(ClassReader.class);
	}

    @Test
	public void generateSimpleClass() throws IOException {
		final ClassFile cf = new ClassFile();
		cf.classname("test/Simple").superclass("java/lang/Object");

		byte[] bytes = cf.toByteArray();
		Util.validate("test.Simple", bytes);
	}

    @Test
	public void generateComplexClass() {
		final ClassFile cf = new ClassFile();
		cf.classname("test/Complex").superclass("java/lang/Object");
		cf.addInterface("java/io/Serializable");

		FieldInfo f = new FieldInfo(cf);
		f.name("sample");
		f.typeDescriptor(new TypeDescriptor(Type.CLASS, 0, "java/lang/String"));
		f.flags().set(Access.PROTECTED);
		cf.addField(f);

		GenericAttribute a = new GenericAttribute(f, Utf8.create("AnyAttribute"));
		a.data(new byte[1024]);
		f.addAttribute(a);

		Util.validate("test.Complex", cf.toByteArray());
	}

//	public void loadAndSaveHelloWorld() throws IOException {
//		byte[] bytes = Util.getByteCode(HelloWorld.class);
//		ClassFile clsfile = new ClassFile();
//		clsfile.load(new ByteArrayDataInput(bytes));
//		clsfile.save(new XDataOutputWrapper(new FileOutputStream("h:\\projects\\jackie\\installed\\test.class")));
//	}

	private void loadSaveValidate(Class cls) {
		byte[] original = Util.getByteCode(cls);
		Util.validate(cls.getName(), original);
	}

}

