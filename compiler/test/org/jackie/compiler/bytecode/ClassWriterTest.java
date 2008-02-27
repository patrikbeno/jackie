package org.jackie.compiler.bytecode;

import org.jackie.compiler.common.TestCase;
import org.jackie.compiler.jmodelimpl.JClassImpl;
import static org.jackie.compiler.util.Context.context;

import org.testng.annotations.Test;

/**
 * @author Patrik Beno
 */
@Test
public class ClassWriterTest extends TestCase {


	public void compile() {
		readClass(Sample.class);
		JClassImpl jclass = context().typeRegistry().getJClass(Sample.class);
		byte[] bytes = jclass.compile();
	}

}
