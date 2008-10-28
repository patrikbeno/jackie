package org.jackie.test.jclassfile;

import static org.jackie.context.ContextManager.*;
import org.jackie.jclassfile.attribute.AttributeProviderRegistry;
import org.jackie.jclassfile.attribute.anno.RuntimeVisibleAnnotations;
import org.jackie.jclassfile.model.ClassFile;
import org.jackie.test.java5.annotation.Explicit;
import org.jackie.utils.XDataInput;
import org.jackie.utils.DataInputWrapper;
import org.testng.annotations.Test;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * @author Patrik Beno
 */
@Test
public class AnnotationTest {

	public void loadAnnotations() throws IOException {
		String path = Explicit.class.getName().replace('.', '/') + ".class";
		ClassFile classfile = new ClassFile();

		newContext();
		try {
			AttributeProviderRegistry.instance().addProvider(new RuntimeVisibleAnnotations.Provider());

			XDataInput in = new DataInputWrapper(
					Thread.currentThread().getContextClassLoader().getResourceAsStream(path));
			classfile.load(in);

			System.out.println(classfile);
		} finally {
			closeContext();
		}
	}


}
