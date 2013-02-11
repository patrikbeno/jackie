package org.jackie.compiler.event;

import org.jackie.event.Event;
import org.jackie.jclassfile.model.ClassFile;
import org.jackie.jvm.JClass;
import org.jackie.jvm.structure.JField;

/**
 * @author Patrik Beno
 */
public abstract class JClassEvents implements Event {

	public void loading(JClass jclass) {}

	public void loaded(JClass jclass) {}

}
