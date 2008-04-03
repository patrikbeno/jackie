package org.jackie.java5.annotation.impl;

import org.jackie.jvm.extension.builtin.JPrimitive;
import org.jackie.jvm.JClass;
import org.jackie.java5.annotation.JAnnotation;
import org.jackie.java5.annotation.JAnnotationAttribute;
import org.jackie.java5.annotation.JAnnotationAttributeValue;
import org.jackie.utils.Assert;

import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Patrik Beno
 */
public class AnnotationProxy implements InvocationHandler {

	protected JAnnotation annotation;

	protected Class<? extends Annotation> _class;

	protected WeakReference<Map<String, Object>> _cache;

	public AnnotationProxy(JAnnotation annotation) {
		this.annotation = annotation;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (isInheritedMethod(method)) {
			return method.invoke(annotation(), args);
		}

		String name = method.getName();
		Class<?> type = method.getReturnType();

		Object value = cache().get(name);
		if (value != null) {
			return value;
		}

		value = buildValue(name, type);
		cache().put(name, value);

		return value; // todo Assert.typecast(value, type);
	}

	protected boolean isInheritedMethod(Method m) {
		boolean inherited = false;
		//noinspection ConstantConditions
		inherited |= m.getDeclaringClass().equals(Annotation.class);
		inherited |= m.getDeclaringClass().equals(Object.class);
		return inherited;
	}

	protected Annotation annotation() {
		return new Annotation() {
			public int hashCode() {
				return super.hashCode(); // todo implement this
			}

			public boolean equals(Object obj) {
				return super.equals(obj); // todo implement this
			}

			public String toString() {
				return super.toString(); // todo implement this
			}

			public Class<? extends Annotation> annotationType() {
				return clazz();
			}
		};
	}

	protected Class<? extends Annotation> clazz() {
		if (_class != null) {
			return _class;
		}
		//noinspection unchecked
		_class = load(annotation.getJAnnotationType().node());
		return _class;
	}

	protected Map<String, Object> cache() {
		Map<String, Object> c = (_cache != null) ? _cache.get() : null;
		if (c != null) { return c; }

		c = new HashMap<String, Object>();
		_cache = new WeakReference<Map<String, Object>>(c);

		return c;
	}

	protected Class load(JClass jclass) {
		try {
			return Class.forName(jclass.getFQName(), false, Thread.currentThread().getContextClassLoader()); // fixme wrong classloader
		} catch (ClassNotFoundException e) {
			throw Assert.notYetHandled(e);
		}
	}

	protected Object buildValue(String name, Class<?> type) {
		JAnnotationAttribute attrdef = annotation.getJAnnotationType().getAttribute(name);
		JAnnotationAttributeValue attr = annotation.getAttribute(name);
		if (attr == null) {
			attr = attrdef.getDefaultValue();
			assert attr != null;
		}
		return convert(attr.getValue(), type);
	}

	protected Object convert(Object value, Class type) {
		if (type.isPrimitive()) {
			assert JPrimitive.isObjectWrapper(value.getClass());
			return value;

		} else if (String.class.equals(type)) {
			assert value instanceof String;
			return value;

		} else if (Class.class.equals(type)) {
			assert value instanceof JClass;
			return load((JClass) value);

		} else if (type.isEnum()) {
			assert value instanceof String[];
			//noinspection unchecked
			return Enum.valueOf(type, ((String[])value)[1]);

		} else if (type.isAnnotation()) {
			return ((AnnotationImpl) value).proxy();

		} else if (type.isArray()) {
			return convertArray(value, type);

		} else {
			throw Assert.invariantFailed("Unhandled type: %s", type);
		}
	}

	protected Object convertArray(Object value, Class type) {
		List list = (List) value;
		Object array = Array.newInstance(type.getComponentType(), list.size());
		for (int i=0; i<list.size(); i++) {
			Object o = list.get(i);
			Object converted = convert(o, type.getComponentType());
			Array.set(array, i, converted); // fixme possible bug - modifies original stringarray
		}
		return array;
	}




}
