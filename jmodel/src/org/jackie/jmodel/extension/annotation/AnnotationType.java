package org.jackie.jmodel.extension.annotation;

import org.jackie.jmodel.extension.annotation.JAnnotationAttribute;
import org.jackie.jmodel.extension.Extension;
import org.jackie.jmodel.Editable;
import org.jackie.jmodel.JClass;

import java.util.List;
import java.util.Set;

/**
 * Annotation type declaration (special type of JClass that represents an annotation)
 *
 * @author Patrik Beno
 */
public interface AnnotationType extends Extension<JClass>, Editable<AnnotationType.Editor> {

	/**
	 * Names of the annotation attributes.
	 * @return
	 */
	Set<String> getAttributeNames();

	/**
	 * Returns annotation attribute declaration for a given name
	 * @param name name of the annotation attribute
	 * @return annotation attribute declaration
	 */
	JAnnotationAttribute getAttribute(String name);

	/**
	 * List of the annotation attributes in declaration order
	 * @return list of the attributes
	 */
	List<JAnnotationAttribute> getAttributes();


	public interface Editor extends org.jackie.jmodel.Editor<AnnotationType> {

		Editor addAtribute(JAnnotationAttribute attribute);

	}

}
