package org.jackie.compiler.jmodelimpl.type;

import org.jackie.jmodel.JClass;
import org.jackie.compiler.jmodelimpl.attribute.impl.KindAttribute;
import org.jackie.jmodel.extension.Extension;
import org.jackie.jmodel.extension.ExtensionProvider;
import org.jackie.jmodel.extension.annotation.AnnotationType;

/**
 * @author Patrik Beno
 */
public class AnnotationTypeProvider implements ExtensionProvider<JClass> {

	public Class<? extends Extension> getType() {
		return AnnotationType.class;
	}

	public Extension getExtension(JClass jclass) {
		KindAttribute kind = jclass.attributes().getAttribute(KindAttribute.class);
		assert kind != null;

		switch (kind.getKind()) {
			case ANNOTATION:
				return new AnnotationTypeImpl(jclass);
		}
		return null;
	}
}