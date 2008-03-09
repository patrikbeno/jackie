package org.jackie.compiler.jmodelimpl.type;

import org.jackie.jmodel.JClass;
import org.jackie.jmodel.extension.builtin.ArrayType;
import org.jackie.utils.Assert;

/**
 * @author Patrik Beno
 */
public class ArrayTypeImpl extends AbstractExtension<JClass> implements ArrayType {

	public ArrayTypeImpl(JClass node) {
		super(node);
	}

	public JClass getComponentType() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public int getDimensions() {
		throw Assert.notYetImplemented(); // todo implement this
	}
}
