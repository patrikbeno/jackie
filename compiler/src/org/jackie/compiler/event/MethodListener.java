package org.jackie.compiler.event;

import org.jackie.event.Event;
import org.jackie.jvm.structure.JMethod;

/**
 * @author Patrik Beno
*/
public interface MethodListener extends Event {

	void loaded(JMethod jmethod);

}
