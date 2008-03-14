package org.jackie.compiler.attribute;

import org.jackie.jmodel.attribute.JAttribute;


/**
 * @author Patrik Beno
 */
public class KindAttribute implements JAttribute {

	Kind kind;

	public KindAttribute(Kind kind) {
		this.kind = kind;
	}

	public String getName() {
		return "Kind";
	}

	public Kind getKind() {
		return kind;
	}
}
