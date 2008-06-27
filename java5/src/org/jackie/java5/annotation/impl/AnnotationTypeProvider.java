package org.jackie.java5.annotation.impl;

import org.jackie.compiler.event.AttributeListener;
import org.jackie.compiler.extension.ExtensionProvider;
import org.jackie.compiler.extension.Lifecycle;
import org.jackie.event.Events;
import org.jackie.java5.annotation.AnnotationType;
import org.jackie.jvm.JClass;
import org.jackie.jvm.attribute.JAttribute;
import org.jackie.jvm.attribute.special.Kind;
import org.jackie.jvm.extension.Extension;

/**
 * @author Patrik Beno
 */
public class AnnotationTypeProvider implements ExtensionProvider<JClass>, Lifecycle {

	AttributeListener listener = new AttributeListener() {
		public void attributeAdded(JAttribute attribute) {

		}
	};

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

	public void init() {
		Events.registerEventListener(AttributeListener.class, listener);
	}

	public void done() {
		Events.unregisterEventListener(listener);
	}
}