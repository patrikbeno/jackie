package org.jackie.jmodel.type;

import org.jackie.jmodel.SpecialType;

/**
 * @author Patrik Beno
 */
public interface PrimitiveType extends SpecialType {

	Class getPrimitiveClass();

	Class getObjectWrapperClass();

}
