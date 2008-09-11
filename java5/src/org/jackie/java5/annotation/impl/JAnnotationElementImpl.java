package org.jackie.java5.annotation.impl;

import org.jackie.java5.annotation.AnnotationType;
import org.jackie.java5.annotation.JAnnotation;
import org.jackie.java5.annotation.JAnnotationElement;
import org.jackie.java5.annotation.JAnnotationElementValue;
import org.jackie.java5.annotation.JAnnotations;
import org.jackie.jvm.JClass;
import org.jackie.jvm.attribute.JAttribute;
import org.jackie.jvm.spi.AbstractJNode;
import org.jackie.jvm.structure.JMethod;
import org.jackie.utils.Assert;
import org.jackie.jclassfile.attribute.anno.AnnotationDefault;

/**
 * @author Patrik Beno
 */
public class JAnnotationElementImpl extends AbstractJNode implements JAnnotationElement {

	static class DefaultElement extends JAnnotationElementValueImpl {
		DefaultElement(JAnnotation annotation, JAnnotationElement element, Object value) {
			super(annotation, element, value);
		}
		public boolean isDefault() {
			return true;
		}
	}

	JAnnotationElementValue defaultValue;

	public JAnnotationElementImpl(JMethod jmethod) {
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

	public JAnnotationElementValue getDefaultValue() {
		if (defaultValue != null) { return defaultValue; }

		JAttribute a = jmethod().attributes().getAttribute(AnnotationDefault.NAME);
		AnnotationDefault adflt = (AnnotationDefault) a.getValue();
		defaultValue = new JAnnotationElementValueImpl(null, this, adflt.value());

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

			final JAnnotationElementImpl athis = JAnnotationElementImpl.this;

			public Editor setName(String name) {
				throw Assert.notYetImplemented(); // todo implement this
			}

			public Editor setType(JClass jclass) {
				throw Assert.notYetImplemented(); // todo implement this
			}

			public Editor setDefaultValue(JAnnotationElementValue dflt) {
				athis.defaultValue = dflt;
				return this;
			}

			public JAnnotationElement editable() {
				return JAnnotationElementImpl.this;
			}
		};
	}
}
