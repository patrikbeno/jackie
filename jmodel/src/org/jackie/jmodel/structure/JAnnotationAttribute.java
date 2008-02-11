package org.jackie.jmodel.structure;

import org.jackie.jmodel.JNode;
import org.jackie.jmodel.Named;
import org.jackie.jmodel.Typed;
import org.jackie.jmodel.Annotated;
import org.jackie.jmodel.type.AnnotationType;

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
