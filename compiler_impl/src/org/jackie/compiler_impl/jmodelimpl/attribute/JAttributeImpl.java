package org.jackie.compiler_impl.jmodelimpl.attribute;

import org.jackie.jvm.spi.AbstractJAttribute;
import org.jackie.jvm.JNode;

/**
 * @author Patrik Beno
 */
public class JAttributeImpl<T> extends AbstractJAttribute<T> {

	public JAttributeImpl(JNode owner, String name, T value) {
		super(owner, name, value);
	}
}
