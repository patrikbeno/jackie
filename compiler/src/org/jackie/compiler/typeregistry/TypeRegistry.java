package org.jackie.compiler.typeregistry;

import org.jackie.compiler.jmodelimpl.LoadLevel;
import org.jackie.compiler.util.ClassName;
import org.jackie.jmodel.JClass;

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
