package org.jackie.compiler.jmodelimpl.annotations;

import org.jackie.jmodel.JClass;
import static org.jackie.jmodel.util.JModelUtils.asAnnotation;
import org.jackie.jmodel.extension.annotation.AnnotationType;
import org.jackie.jmodel.structure.JMethod;
import org.jackie.jmodel.extension.annotation.JAnnotationAttribute;
import org.jackie.jmodel.extension.annotation.JAnnotationAttributeValue;
import org.jackie.jmodel.extension.annotation.Annotations;
import org.jackie.utils.Assert;

/**
 * @author Patrik Beno
 */
public class AnnotationAttributeImpl implements JAnnotationAttribute {

	JMethod jmethod;

	JAnnotationAttributeValue defaultValue;

	public AnnotationAttributeImpl(JMethod jmethod, JAnnotationAttributeValue defaultValue) {
		this.jmethod = jmethod;
		this.defaultValue = defaultValue;
	}

	/// Named, Typed ///


	public String getName() {
		return jmethod.getName();
	}

	public JClass getType() {
		return jmethod.getType();
	}

	/// AnnotationAttribute ///

	public AnnotationType getJAnnotationType() {
		return asAnnotation(jmethod.getJClass());
	}

	public JAnnotationAttributeValue getDefaultValue() {
		return defaultValue;
	}

	///

	public Annotations annotations() {
		throw Assert.notYetImplemented(); // todo implement this
	}
}
