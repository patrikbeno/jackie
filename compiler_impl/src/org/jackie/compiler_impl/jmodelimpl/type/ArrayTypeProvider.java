package org.jackie.compiler_impl.jmodelimpl.type;

import org.jackie.compiler.extension.ExtensionProvider;
import org.jackie.jvm.JClass;
import org.jackie.jvm.attribute.JAttribute;
import org.jackie.jvm.attribute.special.Kind;
import org.jackie.jvm.extension.Extension;
import org.jackie.jvm.extension.builtin.ArrayType;

/**
 * @author Patrik Beno
 */
public class ArrayTypeProvider implements ExtensionProvider<JClass> {

	public Class<? extends Extension> getType() {
		return ArrayType.class;
	}

	public Extension<JClass> getExtension(JClass jclass) {
		JAttribute kind = jclass.attributes().getAttribute("Kind");
		if (kind == null || !kind.getValue().equals(Kind.ARRAY)) {
			return null;
		}

		return new ArrayTypeImpl(jclass);
	}
}