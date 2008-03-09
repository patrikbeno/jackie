package org.jackie.jmodel.extension.annotation;

import org.jackie.jmodel.Editable;
import org.jackie.jmodel.JNode;
import org.jackie.jmodel.extension.annotation.AnnotationType;

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
public interface JAnnotation extends JNode, Editable<JAnnotation.Editor> {

	/**
	 * Element this annotation annotates (is attached to): class, field, method, ...
	 * @see JClass
	 * @see org.jackie.jmodel.structure.JField
	 * @see org.jackie.jmodel.structure.JMethod
	 * @see org.jackie.jmodel.structure.JParameter
	 * @return annotated element or null if there is none (this is the case when annotation is nested in another annotation)
	 */
	Annotated getAnnotatedElement();


	/**
	 * Annotation enclosing this annotation.
	 * @return annotation or null if this is top-level annotation
	 */
	JAnnotation getEnclosingAnnotation();

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

	Editor edit();


	public interface Editor extends org.jackie.jmodel.Editor<JAnnotation> {

		Editor addAttributeValue(JAnnotationAttributeValue value);

	}
}
