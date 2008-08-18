package org.jackie.java5.annotation.impl;

import org.jackie.java5.annotation.JAnnotation;
import org.jackie.java5.annotation.JAnnotationAttribute;
import org.jackie.java5.annotation.JAnnotationAttributeValue;
import org.jackie.jvm.spi.AbstractJNode;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class AnnotationAttributeValueImpl extends AbstractJNode implements JAnnotationAttributeValue {

	JAnnotation annotation;

	JAnnotationAttribute attrdef;

	/**
	 * generic value of then attribute (does not depend on annotation or attribute type)
	 */
	Object value;


	public AnnotationAttributeValueImpl(JAnnotation annotation, JAnnotationAttribute attrdef) {
		super(annotation);
		this.annotation = annotation;
		this.attrdef = attrdef;
		if (annotation != null) {
			annotation.edit().addAttributeValue(this);
		}
	}

	public AnnotationAttributeValueImpl(JAnnotation annotation, JAnnotationAttribute attrdef, Object value) {
		this(annotation, attrdef);
		this.value = value;
	}

	public JAnnotation getJAnnotation() {
		return annotation;
	}

	public JAnnotationAttribute getAnnotationAttribute() {
		return attrdef;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		check(value);
		this.value = value;
	}

	public void addValue(Object value) {
		check(value);
		assert this.value instanceof List;
		((List) this.value).add(value);
	}

	public void initArray() {
		this.value = new ArrayList();
	}


	static private void check(Object value) {
		assert allowed(value, String.class, Boolean.class, Character.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Enum.class, Annotation.class, List.class);
	}

	static private boolean allowed(Object value, Class... types) {
		for (Class c : types) {
			if (!c.isInstance(value)) { return false; }
		}
		return true;
	}
}
