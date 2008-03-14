package org.jackie.compiler.jmodelimpl.type;

import org.jackie.jmodel.extension.Extension;
import org.jackie.jmodel.extension.ExtensionProvider;
import org.jackie.java5.base.InterfaceType;
import org.jackie.jmodel.JClass;
import org.jackie.compiler.attribute.KindAttribute;

/**
 * @author Patrik Beno
 */
public class InterfaceTypeProvider implements ExtensionProvider<JClass> {

	public Class<? extends Extension> getType() {
		return InterfaceType.class;
	}

	public Extension<JClass> getExtension(JClass jclass) {
		KindAttribute kind = jclass.attributes().getAttribute(KindAttribute.class);
		assert kind != null;

		switch (kind.getKind()) {
			case INTERFACE:
			case ANNOTATION:
				return new InterfaceTypeImpl(jclass);
		}
		return null;
	}
}