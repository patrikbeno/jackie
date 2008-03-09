package org.jackie.jmodel.structure;

import org.jackie.jmodel.Editable;
import org.jackie.jmodel.JClass;
import org.jackie.jmodel.JNode;
import org.jackie.jmodel.attribute.Attributed;
import org.jackie.jmodel.extension.Extensible;
import org.jackie.jmodel.props.Flagged;
import org.jackie.jmodel.props.Named;
import org.jackie.jmodel.props.Typed;

/**
 * @author Patrik Beno
 */
public interface JVariable<V extends JVariable<V,E,N>, E extends JVariable.Editor<V,E,N>, N extends JNode>
		extends JNode, Named, Typed, Flagged, Attributed, Extensible, Editable<E> {

	N scope();

	public interface Editor<V extends JVariable<V,E,N>, E extends Editor<V,E,N>, N extends JNode>
			extends org.jackie.jmodel.Editor<V> {

		E setScope(V node);

		E setName(String name);

		E setType(JClass type);

	}

}
