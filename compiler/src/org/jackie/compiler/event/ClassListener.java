package org.jackie.compiler.event;

import org.jackie.event.Event;
import org.jackie.jvm.JClass;

/**
 * @author Patrik Beno
 */
public interface ClassListener extends Event {

	void loaded(JClass jclass);
}
