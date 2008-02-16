package org.jackie.compiler.jmodelimpl.annotations;

import org.jackie.compiler.common.TestCase;
import org.jackie.compiler.jmodelimpl.JClassImpl;
import static org.jackie.compiler.util.Context.context;
import org.jackie.utils.Assert;

import org.testng.annotations.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Patrik Beno
 */
@Test
public class AnnotatedImplTest {

	public void run() {
		new TestCase() {{
			readClass(SampleAnnotaton.class);
			readClass(Retention.class);
			readClass(RetentionPolicy.class);

			JClassImpl cls = context().typeRegistry().getJClass(SampleAnnotaton.class);
			cls.annotations.buildfromAsmNodes();
			AnnotationImpl a = cls.annotations.annotations.get(0);
			SampleAnnotaton annotation = (SampleAnnotaton) a.proxy();
			SampleAnnotaton real = SampleAnnotaton.class.getAnnotation(SampleAnnotaton.class);

			Assert.expected(real.value(), annotation.value(), "invalid; default value");
			Assert.expected(real.foo(), annotation.foo(), "invalid: explicit value");

		}};
	}

}

@SampleAnnotaton(foo="bar")
@Retention(RetentionPolicy.RUNTIME)
@interface SampleAnnotaton {
	String value() default "foo bar";
	String foo();
}


