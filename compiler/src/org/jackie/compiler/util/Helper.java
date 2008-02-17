package org.jackie.compiler.util;

import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.jmodelimpl.type.AnnotationTypeImpl;
import org.jackie.compiler.jmodelimpl.type.ArrayTypeImpl;
import org.jackie.compiler.jmodelimpl.type.ClassTypeImpl;
import org.jackie.compiler.jmodelimpl.type.EnumTypeImpl;
import org.jackie.compiler.jmodelimpl.type.PrimitiveTypeImpl;
import org.jackie.compiler.jmodelimpl.type.SpecialTypeImpl;
import org.jackie.utils.Assert;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Iterator;

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

	static public <T> Iterable<T> iterable(final T[] iterable) {
		class IterableIterator implements Iterable<T>, Iterator<T> {

			int offset;

			public Iterator<T> iterator() {
				return this;
			}

			public boolean hasNext() {
				return offset < iterable.length;
			}

			public T next() {
				return iterable[offset++];
			}

			public void remove() {
				throw Assert.unsupported();
			}
		}

		return (iterable != null && iterable.length>0) ? new IterableIterator() : Collections.<T>emptyList();
	}

	static public <T> Iterable<T> iterable(Iterable<T> iterable) {
		return (iterable != null) ? iterable : Collections.<T>emptyList();
	}

	static public <T> Collection<T> collection(Collection<T> col) {
		return (col != null) ? col : Collections.<T>emptyList();
	}

	static public <K,V> Map<K,V> map(Map<K,V> map) {
		return (map != null) ? map : Collections.<K,V>emptyMap();
	}
}
