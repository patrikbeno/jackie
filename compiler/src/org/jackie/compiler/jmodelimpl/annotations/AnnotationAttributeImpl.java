package org.jackie.compiler.jmodelimpl.annotations;

import org.jackie.jmodel.JClass;
import org.jackie.java5.annotation.AnnotationType;
import org.jackie.jmodel.structure.JMethod;
import org.jackie.java5.annotation.JAnnotationAttribute;
import org.jackie.java5.annotation.JAnnotationAttributeValue;
import org.jackie.java5.annotation.Annotations;
import org.jackie.utils.Assert;
import static org.jackie.compiler.util.Helper.asAnnotations;

/**
 * @author Patrik Beno
 */
public class AnnotationAttributeImpl implements JAnnotationAttribute {

	JMethod jmethod;

	JAnnotationAttributeValue defaultValue;

	public AnnotationAttributeImpl(JMethod jmethod) {
		this.jmethod = jmethod;
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
		return jmethod.getJClass().extensions().get(AnnotationType.class);
	}

	public JAnnotationAttributeValue getDefaultValue() {
		return defaultValue;
	}

	///

	public Annotations annotations() {
		return asAnnotations(jmethod);
	}

	public boolean isEditable() {
		return jmethod.getJClass().isEditable(); 
	}

	public Editor edit() {
		return new Editor() {

			final AnnotationAttributeImpl athis = AnnotationAttributeImpl.this;

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
				return AnnotationAttributeImpl.this;
			}
		};
	}
}
