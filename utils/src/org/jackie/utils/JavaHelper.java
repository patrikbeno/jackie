package org.jackie.utils;

/**
 * @author Patrik Beno
 */
public class JavaHelper {

	/**
	 * Syntax cleanup: avoid <code>!condition</code> (exclamation mark can easily be overloooked)
	 * @param condition
	 * @return
	 */
	static public boolean FALSE(boolean condition) {
		return condition == false;
	}

	/**
	 * Syntax cleanup: complementary function for FALSE()
	 * @param condition
	 * @return
	 */
	static public boolean TRUE(boolean condition) {
		return condition == true;
	}

	static public boolean isSet(int flags, int test) {
		return (flags & test) == test;
	}

}
