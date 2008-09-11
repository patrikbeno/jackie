package org.jackie.jvm.extension.builtin;

import static org.jackie.utils.Assert.NOTNULL;
import org.jackie.jvm.JClass;

import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

/**
 * @author Patrik Beno
 */
public enum JPrimitive {

	VOID(void.class, Void.class),

	BOOLEAN(boolean.class, Boolean.class),

	CHAR(char.class, Character.class),

	BYTE(byte.class, Byte.class),
	SHORT(short.class, Short.class),
	INT(int.class, Integer.class),
	LONG(long.class, Long.class),

	FLOAT(float.class, Float.class),
	DOUBLE(double.class, Double.class),

	;


	static private final Map<String,JPrimitive> byname;
	static private final Map<Class,Class> primitivesByWrapper;
	static private final Map<Class,Class> wrappersByPrimitive;

	static {
		int count = values().length;

		Map<String,JPrimitive> _byname = new HashMap<String, JPrimitive>(count);
		Map<Class,Class> _primitivesByWrapper = new HashMap<Class, Class>(count);
		Map<Class,Class> _wrappersByPrimitive = new HashMap<Class, Class>(count);

		for (JPrimitive p : values()) {
			_byname.put(p.getPrimitiveClass().getName(), p);
			_primitivesByWrapper.put(p.getObjectWrapperClass(), p.getPrimitiveClass());
			_wrappersByPrimitive.put(p.getPrimitiveClass(), p.getObjectWrapperClass());
		}
		
		byname = Collections.unmodifiableMap(_byname);
		primitivesByWrapper = Collections.unmodifiableMap(_primitivesByWrapper);
		wrappersByPrimitive = Collections.unmodifiableMap(_wrappersByPrimitive);
	}

	static public JPrimitive forClassName(String name) {
		return byname.get(name);
	}

	static public boolean isObjectWrapper(Class cls) {
		return primitivesByWrapper.containsKey(cls);
	}

	static public Class getPrimitiveForObjectWrapper(Class cls) {
		return primitivesByWrapper.get(cls);
	}

	static public Class getObjectWrapperForPrimitive(Class cls) {
		return wrappersByPrimitive.get(cls);
	}

	static public boolean isPrimitive(JClass jclass) {
		return jclass.extensions().supports(PrimitiveType.class);
	}


	private final Class primitiveClass;
	private final Class objectWrapperClass;

	
	JPrimitive(Class primitiveClass, Class objectWrapperClass) {
		this.primitiveClass = primitiveClass;
		this.objectWrapperClass = objectWrapperClass;
	}

	public Class<?> getPrimitiveClass() {
		return primitiveClass;
	}

	public Class<?> getObjectWrapperClass() {
		return objectWrapperClass;
	}
}
