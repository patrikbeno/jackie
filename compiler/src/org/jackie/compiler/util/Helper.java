package org.jackie.compiler.util;

import org.jackie.utils.Assert;
import static org.jackie.utils.Assert.typecast;
import static org.jackie.compiler.util.Context.context;
import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.jmodel.JClass;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Patrik Beno
 */
public class Helper {

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

	public static boolean empty(Object array) {
		return array == null || Array.getLength(array)==0;
	}

	public static int length(Object array) {
		return (array != null) ?  Array.getLength(array) : 0;
	}

	static public void assertEditable() {
		context().checkEditable();
	}

	static public JClassImpl impl(JClass jclass) {
		return typecast(jclass, JClassImpl.class);
	}
}
