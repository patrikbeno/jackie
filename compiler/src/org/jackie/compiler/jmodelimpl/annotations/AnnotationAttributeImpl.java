package org.jackie.compiler.jmodelimpl.annotations;

import org.jackie.jmodel.JClass;
import static org.jackie.jmodel.util.JModelUtils.asAnnotation;
import org.jackie.jmodel.extension.annotation.AnnotationType;
import org.jackie.jmodel.structure.JMethod;
import org.jackie.jmodel.extension.annotation.JAnnotationAttribute;
import org.jackie.jmodel.extension.annotation.JAnnotationAttributeValue;
import org.jackie.jmodel.extension.annotation.Annotations;
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
		return asAnnotation(jmethod.getJClass());
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
