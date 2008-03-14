package org.jackie.compiler.jmodelimpl.structure;

import org.jackie.jmodel.JClass;
import org.jackie.jmodel.JNode;
import org.jackie.jmodel.attribute.Attributes;
import org.jackie.jmodel.extension.Extensions;
import org.jackie.jmodel.props.Flags;
import org.jackie.jmodel.structure.JVariable;
import org.jackie.utils.Assert;
import org.jackie.compiler.jmodelimpl.attribute.AttributesImpl;
import org.jackie.compiler.jmodelimpl.ExtensionsImpl;
import org.jackie.compiler.jmodelimpl.FlagsImpl;

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
