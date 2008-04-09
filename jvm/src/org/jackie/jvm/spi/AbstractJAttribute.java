package org.jackie.jvm.spi;

import org.jackie.jvm.attribute.JAttribute;
import org.jackie.utils.Assert;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Patrik Beno
 */
public abstract class AbstractJAttribute<T> implements JAttribute<T> {

	protected String name;

	protected T value;

	protected JAttribute<T> next;

	protected AbstractJAttribute(String name, T value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public T getValue() {
		return value;
	}

	public JAttribute<T> next() {
		return next;
	}

	public boolean isEditable() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public Editor<T> edit() {
		return new Editor<T>() {

			final AbstractJAttribute<T> athis = AbstractJAttribute.this;

			public Editor setName(String name) {
				athis.name = name;
				return this;
			}

			public Editor setValue(T value) {
				athis.value = value;
				return this;
			}

			public Editor setNext(JAttribute<T> attribute) {
				next = attribute;
				return this;
			}

			public JAttribute<T> editable() {
				return athis;
			}
		};
	}

	public Iterator<JAttribute<T>> iterator() {
		return new Iterator<JAttribute<T>>() {

			JAttribute<T> next = AbstractJAttribute.this;

			public boolean hasNext() {
				return next != null;
			}

			public JAttribute<T> next() {
				if (next == null) { throw new NoSuchElementException(); }
				JAttribute<T> current = next;
				next = next.next();
				return current;
			}

			public void remove() {
				throw Assert.notYetImplemented(); // todo implement this
			}
		};
	}

}
