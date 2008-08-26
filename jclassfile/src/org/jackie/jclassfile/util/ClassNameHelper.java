package org.jackie.jclassfile.util;

/**
 * @author Patrik Beno
 */
public class ClassNameHelper {

	static public String toJavaClassName(String binaryName) {
		return binaryName.replace('/', '.');
	}

}
