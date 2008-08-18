package org.jackie.java5.annotation.impl;

import org.jackie.compiler.extension.ExtensionProvider;
import org.jackie.compiler.extension.Lifecycle;
import org.jackie.compiler.event.AttributeListener;
import org.jackie.java5.annotation.Annotations;
import org.jackie.jvm.JClass;
import org.jackie.jvm.JNode;
import org.jackie.jvm.attribute.JAttribute;
import org.jackie.jvm.extension.Extension;
import org.jackie.jvm.structure.JField;
import org.jackie.jvm.structure.JMethod;
import org.jackie.event.Events;

/**
 * @author Patrik Beno
 */
public class AnnotationsProvider implements ExtensionProvider, Lifecycle {

	AttributeListener listener = new AttributeListener() {
		public void attributeAdded(JAttribute attribute) {

		}
	};

	public Class getType() {
		return Annotations.class;
	}

	public Extension getExtension(JNode node) {
		assert node != null;

		boolean supported = false;
		supported |= node instanceof JClass;
		supported |= node instanceof JField;
		supported |= node instanceof JMethod;

		if (!supported) {
			return null;
		}

		Annotations annotations = new AnnotationsImpl(node);
		return annotations;
	}

	public void init() {
		Events.registerEventListener(AttributeListener.class, listener);
	}

	public void done() {
		Events.unregisterEventListener(listener);
	}
}
