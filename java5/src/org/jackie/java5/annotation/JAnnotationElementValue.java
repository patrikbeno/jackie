package org.jackie.java5.annotation;

import org.jackie.jvm.JNode;

/**
 * Represents value of the annotation attribute in an annotation instance
 *
 * @author Patrik Beno
 */
public interface JAnnotationElementValue extends JNode {

	/**
	 * Owner of this element value (annotation instance)
	 * @return
	 */
	JAnnotation getJAnnotation();

	/**
	 * Corresponding annotation element declaration
	 * @return
	 */
	JAnnotationElement getJAnnotationElement();

	/**
	 * Assigned value of this annotation element.
	 * @return primitive, String, JAnnotation or array thereof; (enums and class names as string literals)
	 * @see JAnnotationElement#getDefaultValue()
	 */
	Object getValue();

	/**
	 * True if this element value if the default value for a given annotation element; false otherwise.
	 * I.e. true if this element value is declared within its annotation type as an element default value;
	 * false if it is explicitly defined value in the particular annotation instance
	 * @see JAnnotationElement#getDefaultValue()
	 * @return true if this is default element value; false otherwise
	 */
	boolean isDefault();

}
