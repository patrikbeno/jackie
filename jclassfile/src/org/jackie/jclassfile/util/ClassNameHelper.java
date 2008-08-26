package org.jackie.jclassfile.util;

import org.jackie.utils.Assert;

/**
 * @author Patrik Beno
 */
public class ClassNameHelper {

	static public String toJavaClassName(String binaryName) {
		return binaryName.replace('/', '.');
	}

	static public String toBinaryClassName(String javaClassName) {
		return javaClassName.replace('.', '/');
	}
}
