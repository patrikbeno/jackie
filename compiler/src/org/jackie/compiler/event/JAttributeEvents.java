package org.jackie.compiler.event;

import org.jackie.event.Event;
import org.jackie.jvm.attribute.JAttribute;

/**
 * @author Patrik Beno
 */
public abstract class JAttributeEvents implements Event {

	public void added(JAttribute attribute) {}

}