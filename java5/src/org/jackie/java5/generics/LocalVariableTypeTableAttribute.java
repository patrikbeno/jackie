package org.jackie.java5.generics;

import org.jackie.jvm.attribute.JAttribute;
import org.jackie.jvm.spi.AbstractJAttribute;

/**
 * @author Patrik Beno
 */
public class LocalVariableTypeTableAttribute extends AbstractJAttribute {

	public LocalVariableTypeTableAttribute(Object value) {
		super("LocalVariableTypeTable", value);
	}
}
