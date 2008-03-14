package org.jackie.compiler.jmodelimpl.type;

import org.jackie.jmodel.JClass;
import org.jackie.java5.extra.PackageType;

/**
 * @author Patrik Beno
 */
public class PackageTypeImpl extends AbstractExtension<JClass> implements PackageType {

	public PackageTypeImpl(JClass node) {
		super(node);
	}


}
