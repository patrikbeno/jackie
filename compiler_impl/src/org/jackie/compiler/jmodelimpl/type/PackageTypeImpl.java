package org.jackie.compiler.jmodelimpl.type;

import org.jackie.jvm.JClass;
import org.jackie.java5.extra.PackageType;

/**
 * @author Patrik Beno
 */
public class PackageTypeImpl extends AbstractExtension<JClass> implements PackageType {

	public PackageTypeImpl(JClass node) {
		super(node);
	}


}
