package org.jackie.jclassfile.attribute;

import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.model.ConstantPoolSupport;

import java.util.List;

/**
 * @author Patrik Beno
 */
public interface AttributeSupport extends ConstantPoolSupport {

	List<AttributeInfo> attributes();

	void addAttribute(AttributeInfo attribute);

}
