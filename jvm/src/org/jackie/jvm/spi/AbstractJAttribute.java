package org.jackie.jvm.spi;

import org.jackie.jvm.attribute.JAttribute;
import org.jackie.jvm.JNode;

/**
 * @author Patrik Beno
 */
public abstract class AbstractJAttribute implements JAttribute {

	protected JNode owner;

	protected AbstractJAttribute(JNode owner) {
		this.owner = owner;
	}

	public JNode owner() {
		return owner;
	}

	public boolean isEditable() {
		return JModelHelper.isEditable(owner());
	}

}