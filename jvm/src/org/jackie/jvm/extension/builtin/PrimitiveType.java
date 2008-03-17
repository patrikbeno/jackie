package org.jackie.jvm.extension.builtin;

import org.jackie.jvm.extension.Extension;
import org.jackie.jvm.JClass;

/**
 * @author Patrik Beno
 */
public interface PrimitiveType extends Extension<JClass> {

	Class getPrimitiveClass();

	Class getObjectWrapperClass();

}
