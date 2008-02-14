package org.jackie.compiler.jmodelimpl;

import org.jackie.jmodel.JClass;
import org.jackie.jmodel.JPrimitive;
import org.jackie.jmodel.type.PrimitiveType;

/**
 * @author Patrik Beno
 */
public class PrimitiveTypeImpl implements PrimitiveType {

	public final JClassImpl jclass;
	public final JPrimitive primitive;

	public PrimitiveTypeImpl(JClassImpl jclass, JPrimitive primitive) {
		this.jclass = jclass;
		this.primitive = primitive;
	}

	public Class getPrimitiveClass() {
		return primitive.getPrimitiveClass();
	}

	public Class getObjectWrapperClass() {
		return primitive.getObjectWrapperClass();
	}

	public JClass getJClass() {
		return jclass;
	}

	public String getName() {
		return getPrimitiveClass().getName();
	}

	public String getFQName() {
		return getName();
	}
}
