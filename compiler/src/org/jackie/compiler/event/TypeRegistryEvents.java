package org.jackie.compiler.event;

import org.jackie.event.Event;
import org.jackie.jvm.JClass;

/**
 * @author Patrik Beno
 */
public abstract class TypeRegistryEvents implements Event {

	public void created(JClass jclass) {}

}
