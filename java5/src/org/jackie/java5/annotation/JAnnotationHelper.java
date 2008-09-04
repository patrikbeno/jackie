package org.jackie.java5.annotation;

import org.jackie.jvm.JClass;

/**
 * @author Patrik Beno
 */
public class JAnnotationHelper {

	static public boolean isAnnotation(JClass jclass) {
		return jclass.extensions().supports(AnnotationType.class);
	}

}
