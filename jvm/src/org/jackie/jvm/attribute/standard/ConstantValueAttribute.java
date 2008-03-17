package org.jackie.jvm.attribute.standard;

import org.jackie.jvm.attribute.JAttribute;

/**
 * @deprecated Compiler must support this attribute for backward compatibility but its usage is
 *             discouraged. Any field initialization should be done in a dedicated initializer
 *             (method). Compiler implementation should convert all occurences of this attribute
 *             into mentioned initializer
 * 
 * @author Patrik Beno
 */
@Deprecated
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
