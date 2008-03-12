package org.jackie.compiler.jmodelimpl.type;

import org.jackie.compiler.jmodelimpl.attribute.impl.KindAttribute;
import org.jackie.jmodel.JClass;
import org.jackie.jmodel.extension.Extension;
import org.jackie.jmodel.extension.ExtensionProvider;
import org.jackie.jmodel.extension.enumtype.EnumType;

/**
 * @author Patrik Beno
 */
public class EnumTypeProvider implements ExtensionProvider<JClass> {

	public Class<? extends Extension> getType() {
		return EnumType.class;
	}

	public Extension<JClass> getExtension(JClass jclass) {
		KindAttribute kind = jclass.attributes().getAttribute(KindAttribute.class);
		assert kind != null;

		switch (kind.getKind()) {
			case ENUM:
				return new EnumTypeImpl(jclass);
		}
		return null;
	}
}