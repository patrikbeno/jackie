package org.jackie.compiler_impl.jmodelimpl.attribute;

import org.jackie.jvm.spi.AbstractJAttribute;
import org.jackie.jvm.JNode;
import org.jackie.compiler.spi.Compilable;

/**
 * @author Patrik Beno
 */
public abstract class JAttributeImpl<T> extends AbstractJAttribute<T> implements Compilable {

	public JAttributeImpl(JNode owner, String name, T value) {
		super(owner, name, value);
	}

}
