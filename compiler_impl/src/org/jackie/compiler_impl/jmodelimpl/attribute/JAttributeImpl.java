package org.jackie.compiler_impl.jmodelimpl.attribute;

import org.jackie.jclassfile.attribute.AttributeSupport;
import org.jackie.jvm.JNode;
import org.jackie.jvm.spi.AbstractJAttribute;

/**
 * @author Patrik Beno
 */
public abstract class JAttributeImpl<T> extends AbstractJAttribute<T> {

	public JAttributeImpl(JNode owner, String name, T value) {
		super(owner, name, value);
	}

	public abstract void compile(AttributeSupport attributed);

}
