package org.jackie.jvm.extension.builtin;

import org.jackie.jvm.JClass;

/**
 * @author Patrik Beno
 */
public class ArrayTypeHelper {

	static public boolean isArray(JClass jclass) {
		return jclass.extensions().supports(ArrayType.class);
	}

	static public JClass getComponentType(JClass jclass) {
		return jclass.extensions().get(ArrayType.class).getComponentType();
	}
}
