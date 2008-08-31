package org.jackie.test.java5.annotation;

import org.jackie.compiler.typeregistry.TypeRegistry;
import static org.jackie.context.ContextManager.context;
import org.jackie.java5.annotation.JAnnotations;
import org.jackie.jvm.JClass;
import org.jackie.utils.Assert;
import static org.jackie.utils.Assert.doAssert;
import static org.jackie.utils.Assert.NOTNULL;
import org.testng.annotations.Test;

import java.util.Arrays;
import static java.util.Arrays.asList;

/**
 * @author Patrik Beno
 */
public class AnnotatedImplTest extends TestCase {

	@Test
	public void primitives() {
		run(new Runnable() {
			public void run() {
				Class cls = Explicit.class;
				JClass jcls = get(cls);
				assert jcls != null;

				SampleAnnotation proxy = proxy(jcls);
				assert proxy != null;

				SampleAnnotation real = real(cls);
				assert real != null;

				Assert.expected(real.pboolean(), proxy.pboolean(), "pboolean");
				Assert.expected(real.pchar(), proxy.pchar(), "pchar");
				Assert.expected(real.pbyte(), proxy.pbyte(), "pbyte");
				Assert.expected(real.pshort(), proxy.pshort(), "pshort");
				Assert.expected(real.pint(), proxy.pint(), "pint");
				Assert.expected(real.plong(), proxy.plong(), "plong");
				Assert.expected(real.pfloat(), proxy.pfloat(), "pfloat");
				Assert.expected(real.pdouble(), proxy.pdouble(), "pdouble");
			}
		});
	}

	@Test
	void primitivearrays() {
		run(new Runnable() {
			public void run() {
				Class cls = Explicit.class;
				JClass jcls = get(cls);
				SampleAnnotation proxy = proxy(jcls);
				SampleAnnotation real = real(cls);

				doAssert(Arrays.equals(real.apboolean(), proxy.apboolean()), "apboolean");
				doAssert(Arrays.equals(real.apchar(), proxy.apchar()), "apchar");
				doAssert(Arrays.equals(real.apbyte(), proxy.apbyte()), "apbyte");
				doAssert(Arrays.equals(real.apshort(), proxy.apshort()), "apshort");
				doAssert(Arrays.equals(real.apint(), proxy.apint()), "apint");
				doAssert(Arrays.equals(real.aplong(), proxy.aplong()), "aplong");
				doAssert(Arrays.equals(real.apfloat(), proxy.apfloat()), "apfloat");
				doAssert(Arrays.equals(real.apdouble(), proxy.apdouble()), "apdouble");
			}
		});
	}

	@Test
	public void objects() {
		run(new Runnable() {
			public void run() {
				Class cls = Explicit.class;
				JClass jcls = get(cls);
				SampleAnnotation proxy = proxy(jcls);
				SampleAnnotation real = real(cls);

				Assert.expected(real.string(), proxy.string(), "string");
				Assert.expected(real.clazz(), proxy.clazz(), "clazz");
				Assert.expected(real.color(), proxy.color(), "color (enum)");
				Assert.expected(real.annotation(), proxy.annotation(), "annotation");
			}
		});
	}

	@Test
	public void defaults() {
		test(Defaults.class);
	}

	@Test
	public void explicit() {
		test(Explicit.class);
	}

	void test(final Class<?> cls) {
		run(new Runnable() {
			public void run() {
				JClass jcls = NOTNULL(get(cls), "No JClass for %s", cls);

				JAnnotations JAnnotations = jcls.extensions().get(JAnnotations.class);
				assert JAnnotations != null;

				SampleAnnotation proxy = JAnnotations.getAnnotation(SampleAnnotation.class);
				assert proxy != null;

				SampleAnnotation real = cls.getAnnotation(SampleAnnotation.class);
				assert real != null;

				Assert.expected(real.string(), proxy.string(), "invalid: string()");
				Assert.expected(asList(real.astring()), asList(proxy.astring()),
									 "invalid: stringarray()");
			}
		});
	}


	JClass get(Class<?> cls) {
		return context(TypeRegistry.class).getJClass(cls);
	}

	SampleAnnotation proxy(JClass jcls) {
		return jcls.extensions().get(JAnnotations.class).getAnnotation(SampleAnnotation.class);
	}

	SampleAnnotation real(Class<?> cls) {
		return cls.getAnnotation(SampleAnnotation.class);
	}

}
