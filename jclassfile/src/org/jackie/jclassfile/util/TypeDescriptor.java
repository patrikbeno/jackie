package org.jackie.jclassfile.util;

import org.jackie.jclassfile.model.Type;

/**
 * @author Patrik Beno
 */
public class TypeDescriptor {

	String descriptor;

	Type type;
	int dimensions;
	String typename;

	public TypeDescriptor(String descriptor) {
		this.descriptor = descriptor;
		parse();
	}

	private void parse() {
		dimensions = descriptor.lastIndexOf('[') + 1;
		type = Type.forCode(descriptor.charAt(dimensions));
		if (type.type() != null) {
			typename = type.type().getName();
		} else {
			typename = descriptor.substring(dimensions, descriptor.length() - 1);
		}
	}

	public String getDescriptor() {
		return descriptor;
	}

	public String getTypeName() {
		return typename;
	}

	public boolean isPrimitive() {
		return type.isPrimitive();
	}

	public boolean isArray() {
		return getDimensions() > 0;
	}

	public int getDimensions() {
		return dimensions;
	}

}
