package org.jackie.jmodel.type;

import org.jackie.jmodel.JClass;
import org.jackie.jmodel.SpecialType;

/**
 * @author Patrik Beno
 */
public interface ArrayType extends SpecialType {

	JClass getComponentType();

	int getDimensions();

}
