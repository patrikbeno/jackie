package org.jackie.jvm.extension.builtin;

import org.jackie.jvm.JClass;
import org.jackie.jvm.extension.Extension;

/**
 * @author Patrik Beno
 */
public interface PrimitiveType extends Extension<JClass> {

	Class getPrimitiveClass();

	Class getObjectWrapperClass();

}
