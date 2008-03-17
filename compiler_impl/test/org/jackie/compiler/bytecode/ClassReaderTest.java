package org.jackie.compiler.bytecode;

import org.jackie.compiler.TestCase;
import static org.jackie.compiler.Context.context;
import org.jackie.utils.Assert;
import org.jackie.jvm.JClass;

import org.testng.annotations.Test;

/**
 * @author Patrik Beno
 */
@Test
public class ClassReaderTest extends TestCase {

	public void readSample() throws Exception {
		JClass jclass = context().typeRegistry().getJClass(Sample.class);
		Assert.expected(Sample.class.getSimpleName(), jclass.getName(), "invalid simple class name");
		Assert.expected(Sample.class.getName(), jclass.getFQName(), "invalid class fqname");
	}

}
