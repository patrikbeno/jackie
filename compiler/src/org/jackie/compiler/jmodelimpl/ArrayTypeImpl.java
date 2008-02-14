package org.jackie.compiler.jmodelimpl;

import org.jackie.jmodel.SpecialType;
import org.jackie.jmodel.JClass;
import org.jackie.jmodel.type.ArrayType;
import org.jackie.utils.Assert;

/**
 * @author Patrik Beno
 */
public class ArrayTypeImpl implements ArrayType {

	public JClassImpl jclass;

	public int dimensions;

	public ArrayTypeImpl(JClassImpl jclass, int dimensions) {
		this.jclass = jclass;
		this.dimensions = dimensions;
	}

	/// ArrayType ///

	public JClass getComponentType() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public int getDimensions() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public JClass getJClass() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public String getName() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public String getFQName() {
		throw Assert.notYetImplemented(); // todo implement this
	}
}
