package org.jackie.java5.annotation;

import org.jackie.jvm.Editable;
import org.jackie.jvm.JClass;
import org.jackie.jvm.JNode;
import org.jackie.jvm.extension.Extension;

import java.util.List;
import java.util.Set;

/**
 * Annotation type declaration (special type of JClass that represents an annotation)
 *
 * @author Patrik Beno
 */
public interface AnnotationType extends Extension, Editable<AnnotationType.Editor> {

	JClass node();

	/**
	 * Names of the annotation elements.
	 * @return
	 */
	Set<String> getElementNames();

	/**
	 * Returns annotation element declaration for a given name
	 * @param name name of the annotation element
	 * @return annotation element declaration
	 */
	JAnnotationElement getElement(String name);

	/**
	 * List of the annotation elements in declaration order
	 * @return list of the elements
	 */
	List<JAnnotationElement> getElements();


	public interface Editor extends org.jackie.jvm.Editor<AnnotationType> {

		Editor addElement(JAnnotationElement element);

	}

}
