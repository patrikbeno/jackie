package org.jackie.java5.annotation.impl;

import org.jackie.compiler.extension.ExtensionProvider;
import org.jackie.compiler.extension.Lifecycle;
import org.jackie.event.Events;
import org.jackie.java5.annotation.JAnnotations;
import org.jackie.jvm.JClass;
import org.jackie.jvm.JNode;
import org.jackie.jvm.attribute.JAttribute;
import org.jackie.jvm.extension.Extension;
import org.jackie.jvm.structure.JField;
import org.jackie.jvm.structure.JMethod;
import org.jackie.utils.Assert;

/**
 * @author Patrik Beno
 */
public class AnnotationsProvider implements ExtensionProvider {

	public Class getType() {
		return JAnnotations.class;
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

		JAnnotations JAnnotations = new JAnnotationsImpl(node);
		return JAnnotations;
	}

	public void init() {
	}

	public void done() {
	}
}
