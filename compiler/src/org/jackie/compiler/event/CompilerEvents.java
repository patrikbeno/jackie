package org.jackie.compiler.event;

import org.jackie.event.Event;

/**
 * @author Patrik Beno
 */
public abstract class CompilerEvents implements Event {

	public void compilationStarted() {}

	public void compilationCompleted() {}

}
