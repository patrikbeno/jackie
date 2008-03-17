package org.jackie.java5.annotation;

import org.jackie.jvm.JNode;
import org.jackie.jvm.Editable;
import org.jackie.jvm.JClass;
import org.jackie.jvm.props.Named;
import org.jackie.jvm.props.Typed;
import org.jackie.java5.annotation.AnnotationType;

/**
 * Annotation attribute declaration (actually special view on a method declaration in
 * the annotation declaration class).
 *
 * todo: provide back view on assocaited JMethod? Whould this be useful or would it just clutter the API?
 *
 * @author Patrik Beno
 */
public interface JAnnotationAttribute extends JNode, Named, Typed, Annotated, Editable<JAnnotationAttribute.Editor> {

	AnnotationType getJAnnotationType(); // owner

	/**
	 * @return
	 */
	JAnnotationAttributeValue getDefaultValue();

	public interface Editor extends org.jackie.jvm.Editor<JAnnotationAttribute> {

		Editor setName(String name);

		Editor setType(JClass jclass);

		Editor setDefaultValue(JAnnotationAttributeValue dflt);

	}

}
