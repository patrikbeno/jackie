package org.jackie.compiler.spi;

import org.jackie.context.Service;
import org.jackie.jvm.attribute.JAttribute;

/**
 * @author Patrik Beno
 */
public interface AttributeFactory extends Service {

	JAttribute createAttribute(String name, Object ... args);

}
