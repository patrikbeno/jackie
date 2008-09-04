package org.jackie.java5.enumtype;

import org.jackie.jvm.JClass;

/**
 * @author Patrik Beno
 */
public class JEnumHelper {

	static public boolean isEnum(JClass jclass) {
		return jclass.extensions().supports(EnumType.class);
	}

}
