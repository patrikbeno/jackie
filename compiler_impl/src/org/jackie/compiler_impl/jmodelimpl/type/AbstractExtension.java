package org.jackie.compiler_impl.jmodelimpl.type;

import org.jackie.jvm.JNode;
import org.jackie.jvm.extension.Extension;

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