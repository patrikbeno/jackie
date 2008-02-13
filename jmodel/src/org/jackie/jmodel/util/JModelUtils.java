package org.jackie.jmodel.util;

import org.jackie.jmodel.type.AnnotationType;
import org.jackie.jmodel.type.ArrayType;
import org.jackie.jmodel.type.ClassType;
import org.jackie.jmodel.type.EnumType;
import org.jackie.jmodel.type.InterfaceType;
import org.jackie.jmodel.type.PrimitiveType;
import org.jackie.jmodel.SpecialType;
import org.jackie.jmodel.JClass;

/**
 * @author Patrik Beno
 */
public class JModelUtils {

	// type check methods

	static public <T extends SpecialType> boolean isSpecialType(JClass cls, Class<T> type) {
		return cls.isSpecialType(type);
	}

	static public boolean isPrimitive(JClass cls) {
		return isSpecialType(cls, PrimitiveType.class);
	}

	static public boolean isArray(JClass cls) {
		return isSpecialType(cls, ArrayType.class);
	}

	static public boolean isClassType(JClass cls) {
		return isSpecialType(cls, ClassType.class);
	}

	static public boolean isInterface(JClass cls) {
		return isSpecialType(cls, InterfaceType.class);
	}

	static public boolean isAnnotation(JClass cls) {
		return isSpecialType(cls, AnnotationType.class);
	}

	static public boolean isEnum(JClass cls) {
		return cls.isSpecialType(EnumType.class);
	}

	/// get as type

	static public <T extends SpecialType> T asSpecialType(JClass cls, Class<T> type) {
		return cls.getSpecialTypeView(type);
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
