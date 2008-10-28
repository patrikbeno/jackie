package org.jackie.jclassfile.util;

/**
 * @author Patrik Beno
 */
public class ClassNameHelper {

	static public String toJavaClassName(String binaryName) {
		return binaryName.replace('/', '.');
	}

	static public String toJavaClassName(TypeDescriptor descriptor) {
		return toJavaClassName(descriptor.getTypeName());
	}

	static public String toBinaryClassName(String javaClassName) {
		String result = javaClassName.replace('.', '/').replace("[]","");
		return result;

//		StringBuilder sb = new StringBuilder();
//		for (int i = 0; i < javaClassName.length(); i++) {
//			char c = javaClassName.charAt(i);
//			switch (c) {
//				case '.':
//					sb.append('/');
//					break;
//				case '[':
//				case ']':
//					break;
//				default:
//					sb.append(c);
//			}
//		}
//		return sb.toString();

	}
}
