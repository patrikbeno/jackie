package org.jackie.compiler_impl.bytecode;

/**
 * @author Patrik Beno
 */
public enum BuiltinAttribute {

	/**
	 * contant; usually used as:
	 * (a) final primitive value on a final field in class or interface;
	 * (b) default value of the annotation attribute
	 */
	ConstantValue,

	/**
	 * list of inner classes declared within this class
	 */
	InnerClasses,

	/**
	 * inner class declared within a method body has this attribute pointing to the enclosing classs
	 */
	EnclosingMethod,

	/**
	 * runtime visible annotations (declared with {@link java.lang.annotation.RetentionPolicy.RUNTIME}
	 */
	RuntimeVisibleAnnotations,

	/**
	 * runtime invisible annotaitons (declared with {@link java.lang.annotation.RetentionPolicy.CLASS}
	 */
	RuntimeInvisibleAnnotations,

	;


}
