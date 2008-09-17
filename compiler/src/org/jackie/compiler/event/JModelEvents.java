package org.jackie.compiler.event;

import org.jackie.event.Event;
import org.jackie.jvm.JClass;

/**
 * @author Patrik Beno
 */
public abstract class JModelEvents implements Event {

	public void classAdded(JClass jclass) {}

	public void classRemoved(JClass jclass) {}

}
