package org.jackie.compiler_impl.jmodelimpl.structure;

import org.jackie.jvm.JClass;
import org.jackie.jvm.JNode;
import org.jackie.jvm.attribute.Attributes;
import org.jackie.jvm.extension.Extensions;
import org.jackie.jvm.props.Flags;
import org.jackie.jvm.structure.JVariable;
import org.jackie.compiler_impl.jmodelimpl.attribute.AttributesImpl;
import org.jackie.compiler_impl.jmodelimpl.ExtensionsImpl;
import org.jackie.compiler_impl.jmodelimpl.FlagsImpl;

/**
 * @author Patrik Beno
 */
public abstract class JVariableImpl<N extends JNode> implements JVariable<N> {

	protected N scope;
	protected String name;
	protected JClass type;
	protected Flags flags;
	protected Attributes attributes;
	protected Extensions extensions;

	public N scope() {
		return scope;
	}

	public String getName() {
		return name;
	}

	public JClass getType() {
		return type;
	}

	public Flags flags() {
		if (flags == null) {
			flags = new FlagsImpl();
		}
		return flags;
	}

	public Attributes attributes() {
		if (attributes == null) {
			attributes = new AttributesImpl();
		}
		return attributes;
	}

	public Extensions extensions() {
		if (extensions == null) {
			extensions = new ExtensionsImpl(this);
		}
		return extensions;
	}

}
