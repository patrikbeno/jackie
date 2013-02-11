package org.jackie.jclassfile.attribute;

import org.jackie.jclassfile.model.AttributeInfo;

import java.util.List;

/**
 * @author Patrik Beno
 */
public interface AttributeSupport {

	List<AttributeInfo> attributes();

	void addAttribute(AttributeInfo attribute);

}
