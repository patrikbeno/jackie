package org.jackie.compiler.attribute;

import org.jackie.jvm.attribute.JAttribute;

import java.util.Set;

/**
 * @author Patrik Beno
 */
public interface AttributeProvider {

	Set<String> getSupportedAttributeNames();

	<T extends JAttribute<?>> T getAttribute(String name);


}
