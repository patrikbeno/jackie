package org.jackie.jmodel.type;

import org.jackie.jmodel.JType;

/**
 * @author Patrik Beno
 */
public enum JPrimitive implements JType {

	VOID(void.class),

	BOOLEAN(boolean.class),

	CHAR(char.class),

	BYTE(byte.class),
	SHORT(short.class),
	INT(int.class),
	LONG(long.class),

	FLOAT(float.class),
	DOUBLE(double.class),;

	private final Class clazz;

	JPrimitive(Class clazz) {
		this.clazz = clazz;
	}

	public String getName() {
		return clazz.getName();
	}

	public Class asClass() {
		return clazz;
	}

}
