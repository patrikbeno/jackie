package org.jackie.java5.annotation;

import org.jackie.jmodel.JNode;

/**
 * Represents value of the annotation attribute in an annotation instance
 *
 * @author Patrik Beno
 */
public interface JAnnotationAttributeValue extends JNode {

	/**
	 * Owner of this attribute value (annotation instance)
	 * @return
	 */
	JAnnotation getJAnnotation();

	/**
	 * Corresponding annotation attribute declaration
	 * @return
	 */
	JAnnotationAttribute getAnnotationAttribute();

	/**
	 * Assigned value of this annotation attribute
	 * @return primitive, String, JAnnotation or array thereof; enums as string literals
	 * @see JAnnotationAttribute#getDefaultValue() 
	 */
	Object getValue(); // todo if this returns value (constant) what about nested annotations? return proxies? or JAnnotationInstances? 

}
