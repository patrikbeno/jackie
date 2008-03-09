package org.jackie.compiler.bytecode;

import org.jackie.compiler.common.TestCase;
import org.jackie.compiler.jmodelimpl.JClassImpl;
import static org.jackie.compiler.util.Context.context;
import org.jackie.jmodel.JClass;

import org.testng.annotations.Test;

/**
 * @author Patrik Beno
 */
@Test
public class ClassWriterTest extends TestCase {


//	public void compile() {
//		readClass(Sample.class);
//		final JClass jclass = context().typeRegistry().getJClass(Sample.class);
//		final byte[] bytes = jclass.compile();
//		new ClassLoader() {{
//			Class<?> cls = defineClass(jclass.getFQName(), bytes, 0, bytes.length);
//			System.out.println(cls);
//			System.out.println(cls.getSuperclass());
//		}};
//	}

}
