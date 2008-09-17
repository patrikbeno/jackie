package org.jackie.java5.extra.impl;

import org.jackie.compiler.extension.ExtensionProvider;
import org.jackie.java5.extra.PackageType;
import org.jackie.jvm.JClass;
import org.jackie.jvm.extension.Extension;
import org.jackie.utils.Assert;

/**
 * @author Patrik Beno
 */
public class PackageTypeProvider implements ExtensionProvider<JClass> {

	public Class<? extends Extension> getType() {
		return PackageType.class;
	}

	public Extension getExtension(JClass jclass) {
		// @failfast
		if (!jclass.getName().equals("package-info")) {
			return null;
		}
		return new PackageTypeImpl(jclass);
	}

	public void init() {
		throw Assert.notYetImplemented(); // todo implement this 
	}

	public void done() {
		throw Assert.notYetImplemented(); // todo implement this
	}
}