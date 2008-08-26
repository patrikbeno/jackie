package org.jackie.java5.generics;

import org.jackie.jvm.JNode;
import org.jackie.jvm.spi.AbstractJAttribute;

/**
 * @author Patrik Beno
 */
public class SignatureAttribute extends AbstractJAttribute {

	public SignatureAttribute(JNode owner, Object value) {
		super(owner, "Signature", value);
	}
}
