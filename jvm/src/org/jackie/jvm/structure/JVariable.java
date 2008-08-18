package org.jackie.jvm.structure;

import org.jackie.jvm.Editable;
import org.jackie.jvm.JClass;
import org.jackie.jvm.JNode;
import org.jackie.jvm.attribute.Attributed;
import org.jackie.jvm.extension.Extensible;
import org.jackie.jvm.props.Flagged;
import org.jackie.jvm.props.Named;
import org.jackie.jvm.props.Typed;

/**
 * @author Patrik Beno
 */
public interface JVariable<N extends JNode> extends JNode, Named, Typed, Flagged, Attributed, Extensible, Editable<JVariable.Editor> {

	N scope();

	public interface Editor<T extends JVariable<N>, N extends JNode> extends org.jackie.jvm.Editor<JVariable> {

		Editor setName(String name);

		Editor setType(JClass type);

	}

}
