package org.jackie.compiler.jmodelimpl.type;

import org.jackie.compiler.jmodelimpl.JClassImpl;

/**
 * @author Patrik Beno
 */
public class EnumTypeImpl implements SpecialTypeImpl {

	JClassImpl jclass;

	public EnumTypeImpl(JClassImpl jclass) {
		this.jclass = jclass;
	}
}
