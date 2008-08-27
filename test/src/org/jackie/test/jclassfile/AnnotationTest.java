package org.jackie.test.jclassfile;

import org.testng.annotations.Test;
import org.jackie.jclassfile.model.ClassFile;
import org.jackie.jclassfile.attribute.AttributeProviderRegistry;
import org.jackie.jclassfile.attribute.anno.RuntimeVisibleAnnotations;
import org.jackie.jclassfile.ClassFileContext;
import static org.jackie.jclassfile.ClassFileContext.classFileContext;
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
		String path = Explicit.class.getName().replace('.', '/') + ".class";

		newContext();
		try {
			context().set(
					ClassFileContext.class,
					new ClassFileContext(new ClassFile(), new AttributeProviderRegistry()));

			ClassFileContext ctx = classFileContext();
			ctx.attributeProviderRegistry().addProvider(new RuntimeVisibleAnnotations.Provider());

			DataInput in = new DataInputStream(Thread.currentThread().getContextClassLoader().getResourceAsStream(path));
			ctx.classFile().load(in);

			System.out.println(ctx.classFile());
		} finally {
			closeContext();
		}
	}


}
