package org.jackie.java5.generics;

import org.jackie.jvm.structure.JMethod;
import org.jackie.jvm.spi.AbstractJAttribute;
import org.jackie.jvm.JNode;

/**
 * @author Patrik Beno
 */
public abstract class LocalVariableTypeTableAttribute extends AbstractJAttribute {

	public LocalVariableTypeTableAttribute(JNode owner) {
		super(owner);
	}


}
