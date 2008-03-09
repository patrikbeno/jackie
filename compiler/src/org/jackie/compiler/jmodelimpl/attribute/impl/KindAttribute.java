package org.jackie.compiler.jmodelimpl.attribute.impl;

import org.jackie.compiler.jmodelimpl.attribute.JAttributeImpl;

/**
 * @author Patrik Beno
 */
public class KindAttribute extends JAttributeImpl {

	Kind kind;

	public KindAttribute(Kind kind) {
		super("Kind");
		this.kind = kind;
	}

	public Kind getKind() {
		return kind;
	}
}
