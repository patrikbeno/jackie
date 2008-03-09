package org.jackie.compiler.jmodelimpl.type;

import org.jackie.jmodel.JNode;
import org.jackie.jmodel.extension.Extension;

/**
 * @author Patrik Beno
 */
public abstract class AbstractExtension<T extends JNode> implements Extension<T> {

	protected T node;

	public AbstractExtension(T node) {
		this.node = node;
	}

	public T node() {
		return node;
	}

}