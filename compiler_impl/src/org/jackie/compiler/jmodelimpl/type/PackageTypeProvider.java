package org.jackie.compiler.jmodelimpl.type;

import org.jackie.jvm.extension.Extension;
import org.jackie.jvm.extension.ExtensionProvider;
import org.jackie.java5.extra.PackageType;
import org.jackie.jvm.JClass;

/**
 * @author Patrik Beno
 */
public class PackageTypeProvider implements ExtensionProvider<JClass> {

	public Class<? extends Extension> getType() {
		return PackageType.class;
	}

	public Extension<JClass> getExtension(JClass jclass) {
		// @failfast
		if (!jclass.getName().equals("package-info")) {
			return null;
		}
		return new PackageTypeImpl(jclass);
	}
}