package org.jackie.java5.annotation;

import org.jackie.jvm.Editable;
import org.jackie.jvm.JNode;
import org.jackie.jvm.props.Typed;

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
public interface JAnnotation extends JNode, Typed, Editable<JAnnotation.Editor> {

	/**
	 * Element this annotation annotates (is attached to): class, field, method, or null if there
	 * is no such element (true for nested annotations).
	 * @see #owner()
	 * @see org.jackie.jvm.JClass
	 * @see org.jackie.jvm.structure.JField
	 * @see org.jackie.jvm.structure.JMethod
	 * @see org.jackie.jvm.structure.JParameter
	 * @return annotated element or null if there is none (this is the case when annotation is nested in another annotation)
	 */
	Annotated getAnnotatedElement();

	/**
	 * Type of this annotation
	 */
	AnnotationType getJAnnotationType();

	/**
	 * All available element values for this annotation
	 * @return
	 */
	List<JAnnotationElementValue> getElementValues();

	/**
	 * Returns value of the annotation element for a given name
	 * @param name annotation element name
	 * @return annotation attribute value or null (if there is no value for such an attribute)
	 */
	JAnnotationElementValue getElementValue(String name);

	Editor edit();


	public interface Editor extends org.jackie.jvm.Editor<JAnnotation> {

		Editor addAttributeValue(JAnnotationElementValue value);

	}
}
