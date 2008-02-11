package org.jackie.jmodel.type;

import org.jackie.jmodel.JType;

/**
 * @author Patrik Beno
 */
public interface JArray extends JType {

	JType getComponentType();

	int getDimensions();

}
