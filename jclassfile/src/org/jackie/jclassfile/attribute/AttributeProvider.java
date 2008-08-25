package org.jackie.jclassfile.attribute;

import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.model.ClassFileProvider;
import org.jackie.jclassfile.constantpool.impl.Utf8;

import java.io.DataInput;

/**
 * @author Patrik Beno
 */
public abstract class AttributeProvider {

	String name;

	protected AttributeProvider(String name) {
		this.name = name;
	}

	public String name() {
		return name;
	}

	public abstract AttributeInfo createAttribute(ClassFileProvider owner);

}
