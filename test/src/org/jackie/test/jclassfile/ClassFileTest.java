package org.jackie.test.jclassfile;

import org.jackie.jclassfile.attribute.GenericAttribute;
import org.jackie.jclassfile.flags.Access;
import org.jackie.jclassfile.model.ClassFile;
import org.jackie.jclassfile.model.FieldInfo;
import org.jackie.jclassfile.model.Type;
import org.jackie.jclassfile.util.TypeDescriptor;
import org.objectweb.asm.ClassReader;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * @author Patrik Beno
 */
@Test
public class ClassFileTest {

	public void loadAndSaveSample() throws Exception {
		loadSaveValidate(Sample.class);
	}

	public void loadAndSaveClassReader() throws Exception {
		loadSaveValidate(ClassReader.class);
	}

	public void generateSimpleClass() throws IOException {
		final ClassFile cf = new ClassFile();
		cf.classname("test/Simple").superclass("java/lang/Object");

		byte[] bytes = cf.toByteArray();
		Util.validate("test.Simple", bytes);
	}


	public void generateComplexClass() {
		final ClassFile cf = new ClassFile();
		cf.classname("test/Complex").superclass("java/lang/Object");
		cf.addInterface("java/io/Serializable");

		FieldInfo f = new FieldInfo(cf);
		f.name("sample");
		f.typeDescriptor(new TypeDescriptor(Type.CLASS, 0, "java/lang/String"));
		f.flags().set(Access.PROTECTED);
		cf.addField(f);

		GenericAttribute a = new GenericAttribute(f, cf.constantPool().factory().getUtf8("AnyAttribute"));
		a.data(new byte[1024]);
		f.addAttribute(a);

		Util.validate("test.Complex", cf.toByteArray());
	}

	private void loadSaveValidate(Class cls) {
		byte[] original = Util.getByteCode(cls);
		Util.validate(cls.getName(), original);
	}

}

