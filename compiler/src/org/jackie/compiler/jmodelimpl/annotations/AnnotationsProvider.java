package org.jackie.compiler.jmodelimpl.annotations;

import org.jackie.jmodel.extension.ExtensionProvider;
import org.jackie.jmodel.extension.Extension;
import org.jackie.jmodel.extension.annotation.Annotations;
import org.jackie.jmodel.JNode;
import org.jackie.jmodel.JClass;
import org.jackie.jmodel.structure.JField;
import org.jackie.jmodel.structure.JMethod;
import org.jackie.utils.Assert;

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
