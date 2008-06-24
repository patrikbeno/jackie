package org.jackie.compiler.attribute;

import org.jackie.event.Event;
import org.jackie.jvm.attribute.JAttribute;

/**
 * @author Patrik Beno
 */
public interface AttributeListener extends Event {

	void attributeAdded(JAttribute attribute);

}
