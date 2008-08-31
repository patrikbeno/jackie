package org.jackie.java5.extra.impl;

import org.jackie.java5.AbstractExtension;
import org.jackie.java5.extra.PackageType;
import org.jackie.jvm.JClass;

/**
 * @author Patrik Beno
 */
public class PackageTypeImpl extends AbstractExtension<JClass> implements PackageType {

	public PackageTypeImpl(JClass node) {
		super(node);
	}


}
