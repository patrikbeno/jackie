package org.jackie.compiler_impl.jmodelimpl.type;

import org.jackie.jvm.extension.Extension;
import org.jackie.compiler.extension.ExtensionProvider;
import org.jackie.java5.base.InterfaceType;
import org.jackie.jvm.JClass;
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