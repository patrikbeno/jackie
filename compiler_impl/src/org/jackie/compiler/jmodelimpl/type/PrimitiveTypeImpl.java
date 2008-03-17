package org.jackie.compiler.jmodelimpl.type;

import org.jackie.jvm.JClass;
import org.jackie.jvm.extension.builtin.PrimitiveType;
import org.jackie.jvm.extension.builtin.JPrimitive;

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
