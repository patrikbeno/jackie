package org.jackie.jvm.spi;

import org.jackie.jvm.JNode;
import org.jackie.jvm.attribute.JAttribute;

/**
 * @author Patrik Beno
 */
public abstract class AbstractJAttribute<T> extends AbstractJNode implements JAttribute<T> {

	protected String name;

	protected T value;

	protected AbstractJAttribute(JNode owner, String name, T value) {
		super(owner);
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public T getValue() {
		return value;
	}

	public boolean isEditable() {
		return JModelHelper.isEditable(this);
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

			public JAttribute<T> editable() {
				return athis;
			}
		};
	}

}
