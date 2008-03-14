package org.jackie.compiler.jmodelimpl.type;

import org.jackie.jmodel.JClass;
import org.jackie.jmodel.extension.Extension;
import org.jackie.jmodel.extension.ExtensionProvider;
import org.jackie.jmodel.extension.builtin.ArrayType;
import org.jackie.compiler.attribute.KindAttribute;
import org.jackie.compiler.attribute.Kind;

/**
 * @author Patrik Beno
 */
public class ArrayTypeProvider implements ExtensionProvider<JClass> {

	public Class<? extends Extension> getType() {
		return ArrayType.class;
	}

	public Extension<JClass> getExtension(JClass jclass) {
		KindAttribute kind = jclass.attributes().getAttribute(KindAttribute.class);
		if (kind == null || !kind.getKind().equals(Kind.ARRAY)) {
			return null;
		}

		return new ArrayTypeImpl(jclass);
	}
}