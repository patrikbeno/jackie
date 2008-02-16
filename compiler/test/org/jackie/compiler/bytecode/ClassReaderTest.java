package org.jackie.compiler.bytecode;

import org.jackie.compiler.common.TestCase;
import org.jackie.compiler.jmodelimpl.JClassImpl;
import static org.jackie.compiler.util.Context.context;
import org.jackie.utils.Assert;

import org.testng.annotations.Test;

/**
 * @author Patrik Beno
 */
@Test
public class ClassReaderTest {

	public void readSample() throws Exception {
		new TestCase() {{
			readClass(Sample.class);
			JClassImpl jclass = context().typeRegistry().getJClass(Sample.class);
			Assert.expected(Sample.class.getSimpleName(), jclass.name, "invalid simple class name");
			Assert.expected(Sample.class.getName(), jclass.getFQName(), "invalid class fqname");
		}};
	}

}
