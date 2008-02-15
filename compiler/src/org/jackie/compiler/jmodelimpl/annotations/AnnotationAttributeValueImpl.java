package org.jackie.compiler.jmodelimpl.annotations;

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
		annotation.attributes.add(this);
	}

	public AnnotationAttributeValueImpl(AnnotationImpl annotation, AnnotationAttributeImpl attribute, Object value) {
		this(annotation, attribute);
		this.value = value;
	}

	public void addValue(Object value) {
		((List) this.value).add(value);
	}

	public void initArray() {
		this.value = new ArrayList();
	}
}
