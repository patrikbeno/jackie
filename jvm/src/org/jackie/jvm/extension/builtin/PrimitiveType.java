package org.jackie.jvm.extension.builtin;

import org.jackie.jvm.extension.Extension;

/**
 * @author Patrik Beno
 */
public interface PrimitiveType extends Extension {

	Class getPrimitiveClass();

	Class getObjectWrapperClass();

}
