package org.jackie.jclassfile.attribute;

import org.jackie.jclassfile.model.AttributeInfo;

/**
 * @author Patrik Beno
 */
public interface AttributeProvider {

	String name();
	
	AttributeInfo createAttribute(AttributeSupport owner);

}
