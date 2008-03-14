package org.jackie.java5.base.impl;

import org.objectweb.asm.Attribute;
import org.jackie.jmodel.attribute.JAttribute;

/**
 * @author Patrik Beno
 */
public class EnclosingMethodAttribute extends Attribute implements JAttribute {

	public String owner;
	public String name;
	public String desc;

	public EnclosingMethodAttribute(String owner, String name, String desc) {
		super("EnclosingMethod");
		this.owner = owner;
		this.name = name;
		this.desc = desc;
	}

	public String getName() {
		return type; 
	}
}
