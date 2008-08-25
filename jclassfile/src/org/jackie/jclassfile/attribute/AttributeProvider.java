package org.jackie.jclassfile.attribute;

import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.model.ClassFileProvider;

/**
 * @author Patrik Beno
 */
public interface AttributeProvider {

	String name();
	AttributeInfo createAttribute(ClassFileProvider owner);

}
