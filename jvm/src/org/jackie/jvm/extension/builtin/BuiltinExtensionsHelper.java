package org.jackie.jvm.extension.builtin;

import org.jackie.jvm.JClass;

/**
 * @author Patrik Beno
 */
public class BuiltinExtensionsHelper {

	static public boolean isPrimitive(JClass jclass) {
		return jclass.extensions().supports(PrimitiveType.class);
	}

	static public boolean isArray(JClass jclass) {
		return jclass.extensions().supports(ArrayType.class);
	}

}
