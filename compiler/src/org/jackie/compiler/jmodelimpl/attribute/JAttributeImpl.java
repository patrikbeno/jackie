package org.jackie.compiler.jmodelimpl.attribute;

import org.jackie.jmodel.attribute.JAttribute;
import org.jackie.utils.Assert;

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
