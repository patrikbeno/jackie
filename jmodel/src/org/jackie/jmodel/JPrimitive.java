package org.jackie.jmodel;

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
	DOUBLE(double.class, Double.class),;

	private final Class primitiveClass;
	private final Class objectWrapperClass;

	JPrimitive(Class primitiveClass, Class objectWrapperClass) {
		this.primitiveClass = primitiveClass;
		this.objectWrapperClass = objectWrapperClass;
	}

	public Class getPrimitiveClass() {
		return primitiveClass;
	}

	public Class getObjectWrapperClass() {
		return objectWrapperClass;
	}
}
