package org.jackie.compiler_impl.jmodelimpl.type;

import org.jackie.compiler.attribute.KindAttribute;
import org.jackie.jvm.JClass;
import org.jackie.jvm.extension.Extension;
import org.jackie.jvm.extension.ExtensionProvider;
import org.jackie.java5.enumtype.EnumType;

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