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

	public TypeDescriptor(Type type, int dimensions, String typename) {
		this.type = type;
		this.dimensions = dimensions;
		this.typename = typename;
	}

	public String getDescriptor() {
		if (descriptor == null) {
			descriptor = buildDescriptor();
		}
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

	private void parse() {
		dimensions = descriptor.lastIndexOf('[') + 1;
		type = Type.forCode(descriptor.charAt(dimensions));
		if (type.type() != null) {
			typename = type.type().getName();
		} else {
			typename = descriptor.substring(dimensions+1, descriptor.length() - 1);
		}
	}

	protected String buildDescriptor() {
		StringBuilder sb = new StringBuilder();
		for (int count = getDimensions(); count > 0; count--) {
			sb.append('[');
		}
		sb.append(type.code());
		if (!type.isPrimitive()) {
			sb.append(getTypeName()).append(';');
		}
		return sb.toString();
	}

}
