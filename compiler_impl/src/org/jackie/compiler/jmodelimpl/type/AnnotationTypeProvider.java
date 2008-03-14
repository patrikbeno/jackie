package org.jackie.compiler.jmodelimpl.type;

import org.jackie.jmodel.JClass;
import org.jackie.compiler.attribute.KindAttribute;
import org.jackie.jmodel.extension.Extension;
import org.jackie.jmodel.extension.ExtensionProvider;
import org.jackie.java5.annotation.AnnotationType;

/**
 * @author Patrik Beno
 */
public class AnnotationTypeProvider implements ExtensionProvider<JClass> {

	public Class<? extends Extension> getType() {
		return AnnotationType.class;
	}

	public Extension<JClass> getExtension(JClass jclass) {
		KindAttribute kind = jclass.attributes().getAttribute(KindAttribute.class);
		assert kind != null;

		switch (kind.getKind()) {
			case ANNOTATION:
				return new AnnotationTypeImpl(jclass);
		}
		return null;
	}
}