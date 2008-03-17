package org.jackie.java5.annotation.impl;

import org.jackie.compiler.extension.ExtensionProvider;
import org.jackie.jvm.extension.Extension;
import org.jackie.java5.annotation.Annotations;
import org.jackie.jvm.JNode;
import org.jackie.jvm.JClass;
import org.jackie.jvm.structure.JField;
import org.jackie.jvm.structure.JMethod;

/**
 * @author Patrik Beno
 */
public class AnnotationsProvider implements ExtensionProvider {

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
}
