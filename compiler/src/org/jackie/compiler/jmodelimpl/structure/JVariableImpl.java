package org.jackie.compiler.jmodelimpl.structure;

import org.jackie.jmodel.JClass;
import org.jackie.jmodel.JNode;
import org.jackie.jmodel.attribute.Attributes;
import org.jackie.jmodel.extension.Extensions;
import org.jackie.jmodel.props.Flags;
import org.jackie.jmodel.structure.JVariable;
import org.jackie.utils.Assert;

/**
 * @author Patrik Beno
 */
public class JVariableImpl<V extends JVariable<V,E,N>, E extends JVariable.Editor<V,E,N>, N extends JNode>
		implements JVariable<V,E,N> {

	public N scope() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public String getName() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public JClass getType() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public Flags flags() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public Attributes attributes() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public Extensions extensions() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public E edit() {
		//noinspection unchecked
		return (E) new EditorImpl<V,E,N>();
	}

	protected class EditorImpl<V extends JVariable<V,E,N>, E extends JVariable.Editor<V,E,N>, N extends JNode> implements Editor<V,E,N> {

		public E setScope(V node) {
			throw Assert.notYetImplemented(); // todo implement this
		}

		public E setName(String name) {
			throw Assert.notYetImplemented(); // todo implement this
		}

		public E setType(JClass type) {
			throw Assert.notYetImplemented(); // todo implement this
		}

		public V editable() {
			throw Assert.notYetImplemented(); // todo implement this
		}
	}
}
