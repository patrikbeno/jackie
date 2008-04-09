package org.jackie.java5.base.impl;

import org.jackie.jvm.extension.Extension;
import org.jackie.compiler.extension.ExtensionProvider;
import org.jackie.java5.base.InterfaceType;
import org.jackie.jvm.JClass;
import org.jackie.jvm.attribute.special.KindAttribute;
import org.jackie.jvm.attribute.special.Kind;
import org.jackie.jvm.attribute.JAttribute;

/**
 * @author Patrik Beno
 */
public class InterfaceTypeProvider implements ExtensionProvider<JClass> {

	public Class<? extends Extension> getType() {
		return InterfaceType.class;
	}

	public Extension<JClass> getExtension(JClass jclass) {
		JAttribute kind = jclass.attributes().getAttribute("Kind");
		assert kind != null;

		switch ((Kind) kind.getValue()) {
			case INTERFACE:
			case ANNOTATION:
				return new InterfaceTypeImpl(jclass);
		}
		return null;
	}
}