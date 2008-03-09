package org.jackie.jmodel.extension.annotation;

import org.jackie.jmodel.JNode;
import org.jackie.jmodel.props.Named;
import org.jackie.jmodel.props.Typed;
import org.jackie.jmodel.extension.annotation.AnnotationType;

/**
 * Annotation attribute declaration (actually special view on a method declaration in
 * the annotation declaration class).
 *
 * todo: provide back view on assocaited JMethod? Whould this be useful or would it just clutter the API?
 *
 * @author Patrik Beno
 */
public interface JAnnotationAttribute extends JNode, Named, Typed, Annotated {

	AnnotationType getJAnnotationType(); // owner

	/**
	 * @return
	 */
	JAnnotationAttributeValue getDefaultValue();

}
