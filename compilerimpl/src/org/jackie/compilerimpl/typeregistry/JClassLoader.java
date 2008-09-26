package org.jackie.compilerimpl.typeregistry;

import org.jackie.compilerimpl.jmodelimpl.LoadLevel;
import org.jackie.jvm.JClass;

/**
 * 
 * @author Juraj Burian
 *
 */
public interface JClassLoader {

	void load(JClass jClass, LoadLevel level);
}
