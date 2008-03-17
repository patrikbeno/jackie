package org.jackie.compiler_impl.jmodelimpl.type;

import org.jackie.jvm.JClass;
import org.jackie.jvm.extension.Extension;
import org.jackie.compiler.extension.ExtensionProvider;
import org.jackie.jvm.extension.builtin.JPrimitive;
import org.jackie.jvm.extension.builtin.PrimitiveType;

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