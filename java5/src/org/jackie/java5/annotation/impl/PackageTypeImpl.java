package org.jackie.java5.annotation.impl;

import org.jackie.jvm.JClass;
import org.jackie.java5.extra.PackageType;
import org.jackie.java5.AbstractExtension;

/**
 * @author Patrik Beno
 */
public class PackageTypeImpl extends AbstractExtension<JClass> implements PackageType {

	public PackageTypeImpl(JClass node) {
		super(node);
	}


}
