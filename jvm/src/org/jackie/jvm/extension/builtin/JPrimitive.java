package org.jackie.jvm.extension.builtin;

import static org.jackie.utils.Assert.NOTNULL;
import java.util.HashMap;
import java.util.Map;

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
		byname = new HashMap<String, JPrimitive>();
		primitivesByWrapper = new HashMap<Class, Class>();
		wrappersByPrimitive = new HashMap<Class, Class>();
		for (JPrimitive p : values()) {
			byname.put(p.getPrimitiveClass().getName(), p);
			primitivesByWrapper.put(p.getObjectWrapperClass(), p.getPrimitiveClass());
			wrappersByPrimitive.put(p.getPrimitiveClass(), p.getObjectWrapperClass());
		}
	}

	static public JPrimitive forClassName(String name) {
		return NOTNULL(byname.get(name));
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
