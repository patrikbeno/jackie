package org.jackie.java5.enumtype.impl;

import org.jackie.jvm.attribute.special.KindAttribute;
import org.jackie.jvm.attribute.special.Kind;
import org.jackie.jvm.attribute.JAttribute;
import org.jackie.compiler.extension.ExtensionProvider;
import org.jackie.jvm.JClass;
import org.jackie.jvm.extension.Extension;
import org.jackie.java5.enumtype.EnumType;

/**
 * @author Patrik Beno
 */
public class EnumTypeProvider implements ExtensionProvider<JClass> {

	public Class<? extends Extension> getType() {
		return EnumType.class;
	}

	public Extension<JClass> getExtension(JClass jclass) {
		JAttribute kind = jclass.attributes().getAttribute("Kind");
		assert kind != null;

		switch ((Kind) kind.getValue()) {
			case ENUM:
				return new EnumTypeImpl(jclass);
		}
		return null;
	}
}