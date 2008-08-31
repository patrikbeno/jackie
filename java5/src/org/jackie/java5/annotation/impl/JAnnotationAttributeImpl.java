package org.jackie.java5.annotation.impl;

import org.jackie.java5.annotation.AnnotationType;
import org.jackie.java5.annotation.JAnnotations;
import org.jackie.java5.annotation.JAnnotationAttribute;
import org.jackie.java5.annotation.JAnnotationAttributeValue;
import org.jackie.jvm.JClass;
import org.jackie.jvm.spi.AbstractJNode;
import org.jackie.jvm.structure.JMethod;
import org.jackie.utils.Assert;

/**
 * @author Patrik Beno
 */
public class JAnnotationAttributeImpl extends AbstractJNode implements JAnnotationAttribute {

	JAnnotationAttributeValue defaultValue;

	public JAnnotationAttributeImpl(JMethod jmethod) {
		super(jmethod);
	}

	public JMethod jmethod() {
		return (JMethod) owner();
	}

	/// Named, Typed ///


	public String getName() {
		return jmethod().getName();
	}

	public JClass getType() {
		return jmethod().getType();
	}

	/// AnnotationAttribute ///

	public AnnotationType getJAnnotationType() {
		return jmethod().getJClass().extensions().get(AnnotationType.class);
	}

	public JAnnotationAttributeValue getDefaultValue() {
		return defaultValue;
	}

	///

	public JAnnotations annotations() {
		return jmethod().extensions().get(JAnnotations.class);
	}

	public boolean isEditable() {
		return jmethod().getJClass().isEditable(); 
	}

	public Editor edit() {
		return new Editor() {

			final JAnnotationAttributeImpl athis = JAnnotationAttributeImpl.this;

			public Editor setName(String name) {
				throw Assert.notYetImplemented(); // todo implement this
			}

			public Editor setType(JClass jclass) {
				throw Assert.notYetImplemented(); // todo implement this
			}

			public Editor setDefaultValue(JAnnotationAttributeValue dflt) {
				athis.defaultValue = dflt;
				return this;
			}

			public JAnnotationAttribute editable() {
				return JAnnotationAttributeImpl.this;
			}
		};
	}
}
