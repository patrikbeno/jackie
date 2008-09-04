package org.jackie.java5.annotation.impl;

import org.jackie.java5.annotation.JAnnotation;
import org.jackie.java5.annotation.JAnnotationElement;
import org.jackie.java5.annotation.JAnnotationElementValue;
import org.jackie.jvm.spi.AbstractJNode;
import org.jackie.jclassfile.attribute.anno.ElementValue;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class JAnnotationElementValueImpl extends AbstractJNode implements JAnnotationElementValue {

	JAnnotation annotation;

	JAnnotationElement element;

	/**
	 * generic value of then attribute (does not depend on annotation or attribute type)
	 */
	Object value;


	public JAnnotationElementValueImpl(JAnnotation annotation, JAnnotationElement element) {
		super(annotation);
		this.annotation = annotation;
		this.element = element;
		if (annotation != null) {
			annotation.edit().addAttributeValue(this);
		}
	}

	public JAnnotationElementValueImpl(JAnnotation annotation, JAnnotationElement element, Object value) {
		this(annotation, element);
		this.value = value;
	}

	public JAnnotationElementValueImpl(JAnnotation annotation, ElementValue elementValue) {
		super(annotation);
		JAnnotationElement attr = annotation.getJAnnotationType().getElement(elementValue.name());
		
	}

	public JAnnotation getJAnnotation() {
		return annotation;
	}

	public JAnnotationElement getJAnnotationElement() {
		return element;
	}

	public Object getValue() {
		return value;
	}

	public boolean isDefault() {
		return false;
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
