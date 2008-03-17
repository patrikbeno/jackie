package org.jackie.compiler.typeregistry;

import org.jackie.utils.ClassName;
import org.jackie.jvm.JClass;
import org.jackie.compiler.LoadLevel;

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
