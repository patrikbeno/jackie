package org.jackie.java5.generics;

import org.jackie.jvm.spi.AbstractJAttribute;

/**
 * @author Patrik Beno
 */
public class SignatureAttribute extends AbstractJAttribute {

	public SignatureAttribute(Object value) {
		super("Signature", value);
	}
}
