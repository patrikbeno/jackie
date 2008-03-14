package org.jackie.java5.base.impl;

import org.objectweb.asm.Attribute;
import org.jackie.jmodel.attribute.JAttribute;

/**
 * @author Patrik Beno
 */
public class InnerClassesAttribute extends Attribute implements JAttribute {

	public String name;
	public String outerName;
	public String innerName;
	public int access;

	public InnerClassesAttribute(String name, String outerName, String innerName, int access) {
		super("InnerClasses");
		this.name = name;
		this.outerName = outerName;
		this.innerName = innerName;
		this.access = access;
	}

	public String getName() {
		return type;
	}
}
