package org.jackie.compiler.jmodelimpl.type;

import org.jackie.jmodel.JClass;
import org.jackie.jmodel.JPrimitive;
import org.jackie.jmodel.type.PrimitiveType;
import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.utils.Assert;

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
