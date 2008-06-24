package org.jackie.compiler.event;

import org.jackie.event.Event;
import org.jackie.jvm.structure.JField;

/**
 * @author Patrik Beno
*/
public interface FieldListener extends Event {

	void loaded(JField jfield);

}
