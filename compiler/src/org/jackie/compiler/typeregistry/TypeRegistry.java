package org.jackie.compiler.typeregistry;

import org.jackie.utils.ClassName;
import org.jackie.jvm.JClass;
import org.jackie.compiler.LoadLevel;
import org.jackie.context.ContextObject;

/**
 * @author Patrik Beno
 */
public interface TypeRegistry extends ContextObject {

	boolean isEditable();

	void setEditable(boolean editable);

	boolean hasJClass(ClassName clsname);

	JClass getJClass(ClassName clsname);

	JClass getJClass(Class cls);

	void loadJClass(JClass jClass, LoadLevel level);
}
