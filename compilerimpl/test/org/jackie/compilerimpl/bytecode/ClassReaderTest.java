package org.jackie.compilerimpl.bytecode;

import org.jackie.compiler.typeregistry.TypeRegistry;
import org.jackie.compilerimpl.TestCase;
import static org.jackie.context.ContextManager.context;
import org.jackie.jvm.JClass;
import org.jackie.utils.Assert;
import org.junit.Test;

/**
 * @author Patrik Beno
 */
public class ClassReaderTest extends TestCase {

    @Test
	public void readSample() throws Exception {
		run(new Runnable() {
			public void run() {
				JClass jclass = context(TypeRegistry.class).getJClass(Sample.class);
				Assert.expected(Sample.class.getSimpleName(), jclass.getName(), "invalid simple class name");
				Assert.expected(Sample.class.getName(), jclass.getFQName(), "invalid class fqname");
				Assert.expected(Sample.class.getSuperclass().getName(), jclass.getSuperClass().getFQName(), "invalid superclass fqname");
				Assert.expected(Sample.class.getDeclaredFields().length, jclass.getFields().size(), "invalid field count");
				Assert.expected(Sample.class.getDeclaredMethods().length+1, jclass.getMethods().size(), "invalid field count");
			}
		});
	}

}
