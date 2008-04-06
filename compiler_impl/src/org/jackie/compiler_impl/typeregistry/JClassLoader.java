package org.jackie.compiler_impl.typeregistry;

import org.jackie.compiler_impl.jmodelimpl.LoadLevel;
import org.jackie.jvm.JClass;

/**
 * 
 * @author Juraj Burian
 *
 */
public interface JClassLoader {

	void load(JClass jClass, LoadLevel level);
}
