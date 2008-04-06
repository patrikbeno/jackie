package org.jackie.compiler.typeregistry;

import org.jackie.context.ContextObject;
import org.jackie.jvm.JClass;
import org.jackie.utils.ClassName;

/**
 * @author Patrik Beno
 */
public interface TypeRegistry extends ContextObject {

	boolean isEditable();

	void setEditable(boolean editable);

	boolean hasJClass(ClassName clsname);

	JClass getJClass(ClassName clsname);

	JClass getJClass(Class cls);
}
