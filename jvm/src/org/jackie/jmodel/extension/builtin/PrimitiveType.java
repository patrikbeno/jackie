package org.jackie.jmodel.extension.builtin;

import org.jackie.jmodel.extension.Extension;
import org.jackie.jmodel.JClass;

/**
 * @author Patrik Beno
 */
public interface PrimitiveType extends Extension<JClass> {

	Class getPrimitiveClass();

	Class getObjectWrapperClass();

}
