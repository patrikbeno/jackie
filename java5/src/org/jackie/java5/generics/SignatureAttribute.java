package org.jackie.java5.generics;

import org.jackie.jvm.attribute.JAttribute;

/**
 * @author Patrik Beno
 */
public class SignatureAttribute implements JAttribute {

	protected String signature;

	public String getName() {
		return "Signature";
	}

	public SignatureAttribute(String signature) {
		this.signature = signature;
	}
}
