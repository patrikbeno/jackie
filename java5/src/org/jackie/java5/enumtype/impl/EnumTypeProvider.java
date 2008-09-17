package org.jackie.java5.enumtype.impl;

import org.jackie.compiler.extension.ExtensionProvider;
import org.jackie.java5.enumtype.EnumType;
import org.jackie.jvm.JClass;
import org.jackie.jvm.extension.Extension;
import org.jackie.utils.Assert;

/**
 * @author Patrik Beno
 */
public class EnumTypeProvider implements ExtensionProvider<JClass> {

	public Class<? extends Extension> getType() {
		return EnumType.class;
	}

	public Extension getExtension(JClass jclass) {
		return null;
	}

	public void init() {
		throw Assert.notYetImplemented(); // todo implement this 
	}

	public void done() {
		throw Assert.notYetImplemented(); // todo implement this
	}
}