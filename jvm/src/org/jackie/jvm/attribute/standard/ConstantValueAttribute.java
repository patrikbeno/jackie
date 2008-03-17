package org.jackie.jvm.attribute.standard;

import org.jackie.jvm.attribute.JAttribute;

/**
 * @author Patrik Beno
 */
public class ConstantValueAttribute implements JAttribute {

	protected Object value;

	public ConstantValueAttribute(Object value) {
		this.value = value;
	}

	public String getName() {
		return "ConstantValue";
	}

	public Object getValue() {
		return value;
	}

}
