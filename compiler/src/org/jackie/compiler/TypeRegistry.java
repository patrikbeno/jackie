package org.jackie.compiler;

import org.jackie.utils.ClassName;
import org.jackie.jvm.JClass;

/**
 * @author Patrik Beno
 */
public interface TypeRegistry {

	boolean isEditable();

	void setEditable(boolean editable);

	boolean hasJClass(ClassName clsname);

	JClass getJClass(ClassName clsname);

	JClass getJClass(Class cls);

	void loadJClass(JClass jClass, LoadLevel level);
}
