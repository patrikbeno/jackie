package org.jackie.jmodel.type;

import org.jackie.jmodel.structure.JAnnotationAttribute;
import org.jackie.jmodel.FQNamed;

import java.util.Set;
import java.util.List;

/**
 * Annotation type declaration (special type of JClass that represents an annotation)
 *
 * @author Patrik Beno
 */
public interface AnnotationType extends ExtendedType, FQNamed {

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

}
