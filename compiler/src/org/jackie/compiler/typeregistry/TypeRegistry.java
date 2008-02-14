package org.jackie.compiler.typeregistry;

import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.util.ClassName;

/**
 * @author Patrik Beno
 */
public interface TypeRegistry {

	boolean hasJClass(ClassName clsname);

	JClassImpl getJClass(ClassName clsname);

}
