package org.jackie.compiler.jmodelimpl.type;

import org.jackie.jmodel.JClass;
import org.jackie.jmodel.extension.builtin.PrimitiveType;
import org.jackie.jmodel.extension.builtin.JPrimitive;
import org.jackie.utils.Assert;

/**
 * @author Patrik Beno
 */
public class PrimitiveTypeImpl extends AbstractExtension<JClass> implements PrimitiveType {

	public PrimitiveTypeImpl(JClass jclass) {
		super(jclass);
	}

	public Class getPrimitiveClass() {
		return JPrimitive.forClassName(node().getFQName()).getPrimitiveClass();
	}

	public Class getObjectWrapperClass() {
		return JPrimitive.forClassName(node().getFQName()).getObjectWrapperClass();
	}

}
