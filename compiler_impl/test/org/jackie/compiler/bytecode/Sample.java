package org.jackie.compiler.bytecode;

import java.lang.annotation.Documented;

/**
 * @author Patrik Beno
 */
@SampleAnnotation("class Sample")
public class Sample {

	@SampleAnnotation("field string:String")
	String string;

	void doSomething() {} 

}

@Documented
@interface SampleAnnotation {
	String value() default "foo bar";
}

