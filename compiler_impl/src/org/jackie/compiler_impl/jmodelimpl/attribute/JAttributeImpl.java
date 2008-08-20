package org.jackie.compiler_impl.jmodelimpl.attribute;

import org.jackie.jvm.spi.AbstractJAttribute;
import org.jackie.jvm.JNode;
import org.jackie.compiler.spi.Compilable;
import org.jackie.compiler_impl.bytecode.BuiltinAttribute;

/**
 * @author Patrik Beno
 */
public class JAttributeImpl<T> extends AbstractJAttribute<T> implements Compilable {

	public JAttributeImpl(JNode owner, String name, T value) {
		super(owner, name, value);
	}

	public void compile() {
		
	}
}
