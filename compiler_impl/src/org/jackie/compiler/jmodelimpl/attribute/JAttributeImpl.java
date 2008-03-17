package org.jackie.compiler.jmodelimpl.attribute;

import org.jackie.jvm.attribute.JAttribute;

/**
 * @author Patrik Beno
 */
public class JAttributeImpl implements JAttribute {

	String name;

	public JAttributeImpl(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
