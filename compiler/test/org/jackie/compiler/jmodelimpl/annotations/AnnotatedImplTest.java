package org.jackie.compiler.jmodelimpl.annotations;

import org.jackie.compiler.TestCase;
import static org.jackie.compiler.util.Context.context;
import static org.jackie.compiler.util.Helper.asClassType;
import org.jackie.utils.Assert;
import static org.jackie.utils.Assert.doAssert;
import org.jackie.jmodel.JClass;
import org.jackie.jmodel.extension.annotation.JAnnotation;
import org.jackie.jmodel.extension.annotation.Annotations;
import org.testng.annotations.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import static java.util.Arrays.asList;

/**
 * @author Patrik Beno
 */
public class AnnotatedImplTest extends TestCase {

	@Test
	public void primitives() {
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

	@Test
	void primitivearrays() {
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

	@Test
	public void objects() {
		Class cls = Explicit.class;
		JClass jcls = get(cls);
		SampleAnnotation proxy = proxy(jcls);
		SampleAnnotation real = real(cls);

		Assert.expected(real.string(), proxy.string(), "string");
		Assert.expected(real.clazz(), proxy.clazz(), "clazz");
		Assert.expected(real.color(), proxy.color(), "color (enum)");
		Assert.expected(real.annotation(), proxy.annotation(), "annotation");
	}

	@Test
	public void defaults() {
		test(Defaults.class);
	}

	@Test
	public void explicit() {
		test(Explicit.class);
	}

	void test(Class<?> cls) {
		JClass jcls = get(cls);
		Annotations annotations = jcls.extensions().get(Annotations.class);
//		JAnnotation a = annotations.getJAnnotations().get(0); // fixme API
		SampleAnnotation proxy = annotations.getAnnotation(SampleAnnotation.class);
		SampleAnnotation real = cls.getAnnotation(SampleAnnotation.class);

		Assert.expected(real.string(), proxy.string(), "invalid: string()");
		Assert.expected(asList(real.astring()), asList(proxy.astring()), "invalid: stringarray()");
	}


	JClass get(Class<?> cls) {
		return context().typeRegistry().getJClass(cls);
	}

	SampleAnnotation proxy(JClass jcls) {
		return asClassType(jcls).annotations().getAnnotation(SampleAnnotation.class);
	}

	SampleAnnotation real(Class<?> cls) {
		return cls.getAnnotation(SampleAnnotation.class);
	}

}

@Retention(RetentionPolicy.RUNTIME)
@interface SampleAnnotation {
	// primitives
	boolean pboolean() default false;
	char pchar() default Character.MIN_VALUE;
	byte pbyte() default Byte.MIN_VALUE;
	short pshort() default Short.MIN_VALUE;
	int pint() default Integer.MIN_VALUE;
	long plong() default Long.MIN_VALUE;
	float pfloat() default Float.MIN_VALUE;
	double pdouble() default Double.MIN_VALUE;

	// primitive arrays
	boolean[] apboolean() default {true,false};
	char[] apchar() default {Character.MIN_VALUE,Character.MAX_VALUE};
	byte[] apbyte() default {Byte.MIN_VALUE,Byte.MAX_VALUE};
	short[] apshort() default {Short.MIN_VALUE,Short.MAX_VALUE};
	int[] apint() default {Integer.MIN_VALUE,Integer.MAX_VALUE};
	long[] aplong() default {Long.MIN_VALUE,Long.MAX_VALUE};
	float[] apfloat() default {Float.MIN_VALUE,Float.MAX_VALUE};
	double[] apdouble() default {Double.MIN_VALUE,Double.MAX_VALUE};

	// supported objects
	String string() default "string";
	Class clazz() default SampleAnnotation.class;
	Color color() default Color.RED;
	NestedAnnotation annotation() default @NestedAnnotation(true);

	// arrays of supported objects
	String[] astring() default {"foo","bar"};
	Class[] aclazz() default {Object.class,String.class};
	Color[] acolor() default {Color.RED,Color.GREEN,Color.BLUE};
	NestedAnnotation[] aannotation() default {@NestedAnnotation(false),@NestedAnnotation(true)};
}

@SampleAnnotation
class Defaults {}

@SampleAnnotation(string ="foo", astring = {"1","2","3"})
class Explicit {}

enum Color {RED,GREEN,BLUE}

@interface NestedAnnotation {
	boolean value();
}
