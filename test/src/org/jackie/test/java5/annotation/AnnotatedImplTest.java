package org.jackie.test.java5.annotation;

import org.jackie.utils.Assert;
import static org.jackie.utils.Assert.doAssert;
import org.jackie.jvm.JClass;
import org.jackie.java5.annotation.Annotations;
import static org.jackie.context.ContextManager.context;
import static org.jackie.context.ContextManager.newContext;
import static org.jackie.context.ContextManager.closeContext;
import org.jackie.compiler.typeregistry.TypeRegistry;
import org.testng.annotations.Test;

import java.util.Arrays;
import static java.util.Arrays.asList;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

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
				JClass jcls = get(cls);
				Annotations annotations = jcls.extensions().get(Annotations.class);
				assert annotations != null;

				SampleAnnotation proxy = annotations.getAnnotation(SampleAnnotation.class);
				assert proxy != null;

				SampleAnnotation real = cls.getAnnotation(SampleAnnotation.class);
				assert real != null;

				Assert.expected(real.string(), proxy.string(), "invalid: string()");
				Assert.expected(asList(real.astring()), asList(proxy.astring()), "invalid: stringarray()");
			}
		});
	}


	JClass get(Class<?> cls) {
		return context(TypeRegistry.class).getJClass(cls);
	}

	SampleAnnotation proxy(JClass jcls) {
		return jcls.extensions().get(Annotations.class).getAnnotation(SampleAnnotation.class);
	}

	SampleAnnotation real(Class<?> cls) {
		return cls.getAnnotation(SampleAnnotation.class);
	}

}

/**
* @author Patrik Beno
 */
@interface NestedAnnotation {
	public abstract boolean value();
}

/**
* @author Patrik Beno
 */
@Retention(RetentionPolicy.RUNTIME)
@interface SampleAnnotation {
	// primitives
	public abstract boolean pboolean() default false;
	public abstract char pchar() default Character.MIN_VALUE;
	public abstract byte pbyte() default Byte.MIN_VALUE;
	public abstract short pshort() default Short.MIN_VALUE;
	public abstract int pint() default Integer.MIN_VALUE;
	public abstract long plong() default Long.MIN_VALUE;
	public abstract float pfloat() default Float.MIN_VALUE;
	public abstract double pdouble() default Double.MIN_VALUE;

	// primitive arrays
	public abstract boolean[] apboolean() default {true,false};
	public abstract char[] apchar() default {Character.MIN_VALUE,Character.MAX_VALUE};
	public abstract byte[] apbyte() default {Byte.MIN_VALUE,Byte.MAX_VALUE};
	public abstract short[] apshort() default {Short.MIN_VALUE,Short.MAX_VALUE};
	public abstract int[] apint() default {Integer.MIN_VALUE,Integer.MAX_VALUE};
	public abstract long[] aplong() default {Long.MIN_VALUE,Long.MAX_VALUE};
	public abstract float[] apfloat() default {Float.MIN_VALUE,Float.MAX_VALUE};
	public abstract double[] apdouble() default {Double.MIN_VALUE,Double.MAX_VALUE};

	// supported objects
	public abstract String string() default "string";
	public abstract Class clazz() default SampleAnnotation.class;
	public abstract Color color() default Color.RED;
	public abstract NestedAnnotation annotation() default @NestedAnnotation(true);

	// arrays of supported objects
	public abstract String[] astring() default {"foo","bar"};
	public abstract Class[] aclazz() default {Object.class,String.class};
	public abstract Color[] acolor() default {Color.RED,Color.GREEN,Color.BLUE};
	public abstract NestedAnnotation[] aannotation() default {@NestedAnnotation(false),@NestedAnnotation(true)};
}

/**
* @author Patrik Beno
 */
@SampleAnnotation
class Defaults {}

/**
* @author Patrik Beno
 */
@SampleAnnotation(string ="foo", astring = {"1","2","3"})
class Explicit {}

/**
* @author Patrik Beno
 */
enum Color {RED,GREEN,BLUE;
}

