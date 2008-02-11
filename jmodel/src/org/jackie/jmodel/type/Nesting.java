package org.jackie.jmodel.type;

/**
 * @author Patrik Beno
 */
public enum Nesting {

	/**
	 * Top-level class
	 */
	NONE,

	/**
	 * Regular nested class
	 */
	MEMBER,

	/**
	 * Named class defined in method body
	 */
	LOCAL,

	/**
	 * Anonymous class defined in code (usualy method body)
	 */
	ANONYMOUS,

}
