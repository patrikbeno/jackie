package org.jackie.java5.annotation.impl;

import org.jackie.jmodel.attribute.JAttribute;

/**
 * @author Patrik Beno
 */
public class AnnotationDefaultAttribute implements JAttribute {

	public Object value;

	public AnnotationDefaultAttribute(Object value) {
		this.value = value;
	}

	public String getName() {
		return "AnnotationDefault";
	}

}
