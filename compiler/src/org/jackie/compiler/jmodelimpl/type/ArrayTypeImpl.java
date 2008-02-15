package org.jackie.compiler.jmodelimpl.type;

import org.jackie.compiler.jmodelimpl.JClassImpl;

/**
 * @author Patrik Beno
 */
public class ArrayTypeImpl implements SpecialTypeImpl {

	/**
	 * This array class (for array component, check {@link #componentType}
	 */
	public JClassImpl jclass;

	public JClassImpl componentType;
	public int dimensions;

	public ArrayTypeImpl(JClassImpl jclass, JClassImpl componentType, int dimensions) {
		this.jclass = jclass;
		this.componentType = componentType;
		this.dimensions = dimensions;
	}

}
