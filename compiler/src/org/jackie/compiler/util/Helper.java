package org.jackie.compiler.util;

import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.jmodelimpl.type.AnnotationTypeImpl;
import org.jackie.compiler.jmodelimpl.type.ArrayTypeImpl;
import org.jackie.compiler.jmodelimpl.type.ClassTypeImpl;
import org.jackie.compiler.jmodelimpl.type.EnumTypeImpl;
import org.jackie.compiler.jmodelimpl.type.PrimitiveTypeImpl;
import org.jackie.compiler.jmodelimpl.type.SpecialTypeImpl;
import org.jackie.utils.Assert;

/**
 * @author Patrik Beno
 */
public class Helper {

	// type check methods

	static public <T extends SpecialTypeImpl> boolean isSpecialType(JClassImpl cls, Class<T> type) {
		return cls.getCapability(type) != null;
	}

	static public boolean isPrimitive(JClassImpl cls) {
		return isSpecialType(cls, PrimitiveTypeImpl.class);
	}

	static public boolean isArray(JClassImpl cls) {
		return isSpecialType(cls, ArrayTypeImpl.class);
	}

	static public boolean isClassType(JClassImpl cls) {
		return isSpecialType(cls, ClassTypeImpl.class);
	}

	static public boolean isInterface(JClassImpl cls) {
		throw Assert.notYetImplemented(); // todo implement this
	}

	static public boolean isAnnotation(JClassImpl cls) {
		return isSpecialType(cls, AnnotationTypeImpl.class);
	}

	static public boolean isEnum(JClassImpl cls) {
		return isSpecialType(cls, EnumTypeImpl.class);
	}

	/// get as type

	static public <T extends SpecialTypeImpl> T asSpecialType(JClassImpl cls, Class<T> type) {
		return cls.getCapability(type);
	}

	static public PrimitiveTypeImpl asPrimitive(JClassImpl cls) {
		return asSpecialType(cls, PrimitiveTypeImpl.class);
	}

	static public ArrayTypeImpl asArray(JClassImpl cls) {
		return asSpecialType(cls, ArrayTypeImpl.class);
	}

	static public ClassTypeImpl asClass(JClassImpl cls) {
		return asSpecialType(cls, ClassTypeImpl.class);
	}

//	static public InterfaceTypeImpl asInterface(JClassImpl cls) {
//		return asSpecialType(cls, InterfaceTypeImpl.class);
//	}

	static public AnnotationTypeImpl asAnnotation(JClassImpl cls) {
		return asSpecialType(cls, AnnotationTypeImpl.class);
	}

	static public EnumTypeImpl asEnum(JClassImpl cls) {
		return asSpecialType(cls, EnumTypeImpl.class);
	}

}
