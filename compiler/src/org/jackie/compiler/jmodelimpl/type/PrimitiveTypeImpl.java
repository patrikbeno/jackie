package org.jackie.compiler.jmodelimpl.type;

import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.jmodel.JPrimitive;

/**
 * @author Patrik Beno
 */
public class PrimitiveTypeImpl implements SpecialTypeImpl {

	public final JClassImpl jclass;
	public final JPrimitive primitive;

	public PrimitiveTypeImpl(JClassImpl jclass, JPrimitive primitive) {
		this.jclass = jclass;
		this.primitive = primitive;
	}

}
