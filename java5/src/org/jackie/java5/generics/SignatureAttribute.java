package org.jackie.java5.generics;

import org.jackie.jvm.JNode;
import org.jackie.jvm.spi.AbstractJAttribute;

/**
 * @author Patrik Beno
 */
public class SignatureAttribute extends AbstractJAttribute {

	static public final String NAME = "Signature";

	String value;

	public SignatureAttribute(JNode owner, String signature) {
		super(owner);
		this.value = signature;
	}

	public String getName() {
		return NAME;
	}

	public String getValue() {
		return value;
	}

	public interface Editor extends org.jackie.jvm.Editor<SignatureAttribute> {
		Editor setValue(String signature);
	}

	public Editor edit() {
		return new Editor() {
			public Editor setValue(String signature) {
				value = signature;
				return this;
			}
			public SignatureAttribute editable() {
				return SignatureAttribute.this;
			}
		};
	}
}
