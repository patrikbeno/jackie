package org.jackie.jmodel.util;

import org.jackie.jmodel.JClass;
import org.jackie.jmodel.JNode;
import org.jackie.jmodel.extension.Extension;
import org.jackie.jmodel.extension.Extensible;
import org.jackie.jmodel.extension.enumtype.EnumType;
import org.jackie.jmodel.extension.annotation.AnnotationType;
import org.jackie.jmodel.extension.builtin.ArrayType;
import org.jackie.jmodel.extension.base.ClassType;
import org.jackie.jmodel.extension.base.InterfaceType;
import org.jackie.jmodel.extension.builtin.PrimitiveType;

/**
 * @author Patrik Beno
 */
public class JModelUtils {

	// type check methods

	static public <N extends JNode & Extensible, T extends Extension> boolean supportsExtension(N node, Class<T> type) {
		return node.extensions().supports(type);
	}

	static public boolean isPrimitive(JClass cls) {
		return supportsExtension(cls, PrimitiveType.class);
	}

	static public boolean isArray(JClass cls) {
		return supportsExtension(cls, ArrayType.class);
	}

	static public boolean isClassType(JClass cls) {
		return supportsExtension(cls, ClassType.class);
	}

	static public boolean isInterface(JClass cls) {
		return supportsExtension(cls, InterfaceType.class);
	}

	static public boolean isAnnotation(JClass cls) {
		return supportsExtension(cls, AnnotationType.class);
	}

	static public boolean isEnum(JClass cls) {
		return supportsExtension(cls, EnumType.class);
	}

	/// get as type

	static public <N extends JNode & Extensible, T extends Extension> T asSpecialType(N cls, Class<T> type) {
		return cls.extensions().get(type);
	}

	static public PrimitiveType asPrimitive(JClass cls) {
		return asSpecialType(cls, PrimitiveType.class);
	}

	static public ArrayType asArray(JClass cls) {
		return asSpecialType(cls, ArrayType.class);
	}

	static public ClassType asClass(JClass cls) {
		return asSpecialType(cls, ClassType.class);
	}

	static public InterfaceType asInterface(JClass cls) {
		return asSpecialType(cls, InterfaceType.class);
	}

	static public AnnotationType asAnnotation(JClass cls) {
		return asSpecialType(cls, AnnotationType.class);
	}

	static public EnumType asEnum(JClass cls) {
		return asSpecialType(cls, EnumType.class);
	}

}
