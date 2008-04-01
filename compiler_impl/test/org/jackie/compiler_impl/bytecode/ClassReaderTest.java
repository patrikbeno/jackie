package org.jackie.compiler_impl.bytecode;

import org.jackie.compiler_impl.TestCase;
import org.jackie.utils.Assert;
import org.jackie.jvm.JClass;
import org.jackie.compiler.typeregistry.TypeRegistry;import static org.jackie.context.ContextManager.context;

import org.testng.annotations.Test;

/**
 * @author Patrik Beno
 */
@Test
public class ClassReaderTest extends TestCase {

	public void readSample() throws Exception {
		JClass jclass = context(TypeRegistry.class).getJClass(Sample.class);
		Assert.expected(Sample.class.getSimpleName(), jclass.getName(), "invalid simple class name");
		Assert.expected(Sample.class.getName(), jclass.getFQName(), "invalid class fqname");
	}

}
