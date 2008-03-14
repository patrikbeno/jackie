package org.jackie.jmodel.extension.builtin;

import org.jackie.jmodel.JClass;
import org.jackie.jmodel.extension.Extension;

/**
 * @author Patrik Beno
 */
public interface ArrayType extends Extension<JClass> {

	JClass getComponentType();

	int getDimensions();

}
