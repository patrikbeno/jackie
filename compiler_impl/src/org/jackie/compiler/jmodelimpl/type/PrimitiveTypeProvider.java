package org.jackie.compiler.jmodelimpl.type;

import org.jackie.jmodel.JClass;
import org.jackie.jmodel.extension.Extension;
import org.jackie.jmodel.extension.ExtensionProvider;
import org.jackie.jmodel.extension.builtin.JPrimitive;
import org.jackie.jmodel.extension.builtin.PrimitiveType;

/**
 * @author Patrik Beno
 */
public class PrimitiveTypeProvider implements ExtensionProvider<JClass> {

	public Class<? extends Extension> getType() {
		return PrimitiveType.class;
	}

	public Extension<JClass> getExtension(JClass jclass) {
		if (JPrimitive.forClassName(jclass.getFQName()) == null) {
			return null;
		}

		return new PrimitiveTypeImpl(jclass);
	}
}