package org.jackie.jvm.attribute;

import org.jackie.jvm.props.Named;
import org.jackie.jvm.JNode;
import org.jackie.jvm.Editable;
import org.jackie.jvm.Editor;

import java.util.Iterator;

/**
 * @author Patrik Beno
 */
public interface JAttribute<T> extends JNode, Named, Editable<Editor>, Iterable<JAttribute<T>> {

	String getName();

	T getValue();

	JAttribute<T> next();

	Editor<T> edit();

	public interface Editor<T> extends org.jackie.jvm.Editor<JAttribute<T>> {

		Editor setName(String name);

		Editor setValue(T value);

		Editor setNext(JAttribute<T> attribute);
	}
}
