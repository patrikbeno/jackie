package org.jackie.jvm.attribute;

import org.jackie.jvm.Editable;
import org.jackie.jvm.Editor;
import org.jackie.jvm.JNode;
import org.jackie.jvm.props.Named;

/**
 * @author Patrik Beno
 */
public interface JAttribute<T> extends JNode, Named, Editable<Editor> {

	String getName();

	T getValue();

	Editor<T> edit();

	public interface Editor<T> extends org.jackie.jvm.Editor<JAttribute<T>> {

		Editor setName(String name);

		Editor setValue(T value);

	}
}
