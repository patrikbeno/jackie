package org.jackie.compiler_impl.jmodelimpl.attribute;

import org.jackie.jvm.spi.AbstractJAttribute;

/**
 * @author Patrik Beno
 */
public class JAttributeImpl<T> extends AbstractJAttribute<T> {

	public JAttributeImpl(String name, T value) {
		super(name, value);
	}

}
