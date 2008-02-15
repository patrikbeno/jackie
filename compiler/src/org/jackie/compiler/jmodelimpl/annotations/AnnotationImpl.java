package org.jackie.compiler.jmodelimpl.annotations;

import org.jackie.compiler.jmodelimpl.type.AnnotationTypeImpl;
import org.jackie.jmodel.type.AnnotationType;

import java.util.List;

/**
 * @author Patrik Beno
 */
public class AnnotationImpl {

	/**
	 * Owning (wrapping) annotation (in case of nested annotations)
	 */
	public AnnotationImpl owner;

	/**
	 * Annotated element
	 */
	public AnnotatedImpl annotated;

	/**
	 * type of this annotation
	 */
	public AnnotationTypeImpl type;

	/**
	 * Values of the annotation attributes
	 */
	public List<AnnotationAttributeValueImpl> attributes;


	public AnnotationImpl(AnnotationImpl owner, AnnotationType type) {
		this(owner, (AnnotationTypeImpl) type);
	}

	public AnnotationImpl(AnnotationImpl owner, AnnotationTypeImpl type) {
		this.owner = owner;
		this.type = type;
	}

	public AnnotationImpl(AnnotatedImpl annotated, AnnotationTypeImpl type) {
		this.annotated = annotated;
		this.type = type;
	}

	public AnnotationAttributeValueImpl getAttributeValue(AnnotationAttributeImpl attr) {
		for (AnnotationAttributeValueImpl a : attributes) {
			if (a.attribute.equals(attr)) {
				return a;
			}
		}
		return null;
	}

}
