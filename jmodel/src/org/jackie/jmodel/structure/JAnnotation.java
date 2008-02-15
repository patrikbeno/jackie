package org.jackie.jmodel.structure;

import org.jackie.jmodel.JNode;
import org.jackie.jmodel.props.Annotated;
import org.jackie.jmodel.type.AnnotationType;

import java.util.List;

/**
 * Represents concrete annotation instance attached to a given annotatable element
 *
 * todo: Handling of non-defined attributes and defaults?
 * Return null for missing attributes?
 * Or provide default automatically as runtime does?
 *
 * todo: Exception handling?
 * Throw exception if unsupported (undeclared) attribute is requested?
 * Or just return null? 
 *
 * @author Patrik Beno
 */
public interface JAnnotation extends JNode {

	/**
	 * Element this annotation annotates (is attached to): class, field, method, ...
	 * @see JClass
	 * @see JField
	 * @see JMethod
	 * @see JParameter
	 */
	Annotated getAnnotatedElement();

	/**
	 * Type of this annotation
	 */
	AnnotationType getJAnnotationType();

	/**
	 * Returns value of the annotation attribute for a given name
	 * @param name name of the attribute
	 * @return annotation attribute value or null (if there is no value for such an attribute)
	 */
	JAnnotationAttributeValue getAttribute(String name);

	/**
	 * All available attributes for this annotation
	 * @return
	 */
	List<JAnnotationAttributeValue> getAttributes();

}
