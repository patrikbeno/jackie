package org.jackie.compiler.jmodelimpl.type;

import org.jackie.jmodel.JClass;
import org.jackie.jmodel.extension.extra.PackageType;
import org.jackie.utils.Assert;

/**
 * @author Patrik Beno
 */
public class PackageTypeImpl extends AbstractExtension<JClass> implements PackageType {

	public PackageTypeImpl(JClass node) {
		super(node);
	}


}
