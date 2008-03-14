package org.jackie.java5.annotation;

import org.jackie.jmodel.JNode;
import org.jackie.jmodel.Editable;
import org.jackie.jmodel.JClass;
import org.jackie.jmodel.props.Named;
import org.jackie.jmodel.props.Typed;
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

	public interface Editor extends org.jackie.jmodel.Editor<JAnnotationAttribute> {

		Editor setName(String name);

		Editor setType(JClass jclass);

		Editor setDefaultValue(JAnnotationAttributeValue dflt);

	}

}
