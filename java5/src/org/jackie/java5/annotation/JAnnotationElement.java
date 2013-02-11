package org.jackie.java5.annotation;

import org.jackie.jvm.Editable;
import org.jackie.jvm.JClass;
import org.jackie.jvm.JNode;
import org.jackie.jvm.props.Named;
import org.jackie.jvm.props.Typed;

/**
 * Annotation element declaration (actually special view on a method declaration in
 * the annotation declaration class).
 *
 * todo: provide back view on assocaited JMethod? Whould this be useful or would it just clutter the API?
 *
 * @author Patrik Beno
 */
public interface JAnnotationElement extends JNode, Named, Typed, Annotated, Editable<JAnnotationElement.Editor> {

	/**
	 * Owner of this annotation element declaration
	 * @return
	 */
	AnnotationType getJAnnotationType();

	/**
	 * Default value of this element declaration if it has any
	 *
	 * @return
	 */
	JAnnotationElementValue getDefaultValue();


	public interface Editor extends org.jackie.jvm.Editor<JAnnotationElement> {

		Editor setName(String name);

		Editor setType(JClass jclass);

		Editor setDefaultValue(JAnnotationElementValue dflt);

	}

}
