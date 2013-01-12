package org.jackie.test.java5.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
* @author Patrik Beno
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SampleAnnotation {
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
