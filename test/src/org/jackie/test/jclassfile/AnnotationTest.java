package org.jackie.test.jclassfile;

import org.testng.annotations.Test;
import org.jackie.jclassfile.model.ClassFile;
import org.jackie.jclassfile.attribute.AttributeProviderRegistry;
import org.jackie.jclassfile.attribute.anno.RuntimeVisibleAnnotations;
import org.jackie.jclassfile.ClassFileContext;
import static org.jackie.context.ContextManager.newContext;
import static org.jackie.context.ContextManager.closeContext;
import static org.jackie.context.ContextManager.context;
import org.jackie.test.java5.annotation.Explicit;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * @author Patrik Beno
 */
@Test
public class AnnotationTest {

	public void loadAnnotations() throws IOException {
		AttributeProviderRegistry.instance().addProvider(new RuntimeVisibleAnnotations.Provider());
		String path = Explicit.class.getName().replace('.', '/') + ".class";

		newContext();
		try {
			DataInput in = new DataInputStream(Thread.currentThread().getContextClassLoader().getResourceAsStream(path));
			ClassFile classfile = new ClassFile();
			context().set(ClassFileContext.class, new ClassFileContext(classfile));

			classfile.load(in);

			System.out.println(classfile);
		} finally {
			closeContext();
		}
	}


}
