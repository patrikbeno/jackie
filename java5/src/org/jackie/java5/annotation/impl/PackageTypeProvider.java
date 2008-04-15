package org.jackie.java5.annotation.impl;

import org.jackie.compiler.extension.ExtensionProvider;
import org.jackie.java5.extra.PackageType;
import org.jackie.jvm.JClass;
import org.jackie.jvm.extension.Extension;

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