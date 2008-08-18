package org.jackie.java5.generics;

import org.jackie.jvm.spi.AbstractJAttribute;
import org.jackie.jvm.structure.JMethod;

/**
 * @author Patrik Beno
 */
public class LocalVariableTypeTableAttribute extends AbstractJAttribute {

	public LocalVariableTypeTableAttribute(JMethod jmethod, Object value) {
		super(jmethod, "LocalVariableTypeTable", value);
	}
}
