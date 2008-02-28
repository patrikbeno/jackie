package org.jackie.compiler.jmodelimpl.annotations;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class AnnotationAttributeValueImpl {

	public AnnotationImpl annotation;

	public AnnotationAttributeImpl attribute;

	/**
	 * generic value of then attribute (does not depend on annotation or attribute type)
	 */
	public Object value;

	public AnnotationAttributeValueImpl(AnnotationImpl annotation, AnnotationAttributeImpl attribute) {
		this.annotation = annotation;
		this.attribute = attribute;
		if (annotation != null) {
			annotation.addAttributeValue(this);
		}
	}

	public AnnotationAttributeValueImpl(AnnotationImpl annotation, AnnotationAttributeImpl attribute, Object value) {
		this(annotation, attribute);
		this.value = value;
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
