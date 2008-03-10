package org.jackie.compiler.bytecode;

import org.jackie.compiler.TestCase;
import static org.jackie.compiler.util.Context.context;
import org.jackie.utils.Assert;
import org.jackie.jmodel.JClass;

import org.testng.annotations.Test;

/**
 * @author Patrik Beno
 */
@Test
public class ClassReaderTest extends TestCase {

	public void readSample() throws Exception {
		readClass(Sample.class);
		JClass jclass = context().typeRegistry().getJClass(Sample.class);
		Assert.expected(Sample.class.getSimpleName(), jclass.getName(), "invalid simple class name");
		Assert.expected(Sample.class.getName(), jclass.getFQName(), "invalid class fqname");
	}

}
