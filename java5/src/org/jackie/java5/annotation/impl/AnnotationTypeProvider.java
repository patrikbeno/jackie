package org.jackie.java5.annotation.impl;

import org.jackie.jvm.JClass;
import org.jackie.jvm.attribute.special.KindAttribute;
import org.jackie.jvm.attribute.special.Kind;
import org.jackie.jvm.attribute.JAttribute;
import org.jackie.compiler.extension.ExtensionProvider;
import org.jackie.jvm.extension.Extension;
import org.jackie.java5.annotation.AnnotationType;

/**
 * @author Patrik Beno
 */
public class AnnotationTypeProvider implements ExtensionProvider<JClass> {

	public Class<? extends Extension> getType() {
		return AnnotationType.class;
	}

	public Extension<JClass> getExtension(JClass jclass) {
		JAttribute kind = jclass.attributes().getAttribute("Kind");
		assert kind != null;

		switch ((Kind) kind.getValue()) {
			case ANNOTATION:
				return new AnnotationTypeImpl(jclass);
		}
		return null;
	}
}