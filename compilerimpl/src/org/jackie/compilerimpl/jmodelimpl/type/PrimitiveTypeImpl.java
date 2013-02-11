package org.jackie.compilerimpl.jmodelimpl.type;

import org.jackie.jvm.JClass;
import org.jackie.jvm.extension.builtin.JPrimitive;
import org.jackie.jvm.extension.builtin.PrimitiveType;
import org.jackie.jvm.extension.Extension;

/**
 * @author Patrik Beno
 */
public class PrimitiveTypeImpl extends AbstractExtension<JClass> implements PrimitiveType {

	public PrimitiveTypeImpl(JClass jclass) {
		super(jclass);
	}

	public Class<? extends Extension> type() {
		return PrimitiveType.class;
	}

	public Class getPrimitiveClass() {
		return JPrimitive.forClassName(node().getFQName()).getPrimitiveClass();
	}

	public Class getObjectWrapperClass() {
		return JPrimitive.forClassName(node().getFQName()).getObjectWrapperClass();
	}

}
