package org.jackie.jmodel.extension.base;

/**
 * @author Patrik Beno
	 */
	public enum ClassNestingLevel {

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
