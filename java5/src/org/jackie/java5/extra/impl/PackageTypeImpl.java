package org.jackie.java5.extra.impl;

import org.jackie.java5.AbstractExtension;
import org.jackie.java5.extra.PackageType;
import org.jackie.jvm.JClass;
import org.jackie.jvm.extension.Extension;

/**
 * @author Patrik Beno
 */
public class PackageTypeImpl extends AbstractExtension<JClass> implements PackageType {

	public PackageTypeImpl(JClass node) {
		super(node);
	}

	public Class<? extends Extension> type() {
		return PackageType.class;
	}
}
