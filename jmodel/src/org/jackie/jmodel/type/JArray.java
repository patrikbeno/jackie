package org.jackie.jmodel.type;

import org.jackie.jmodel.JType;
import org.jackie.jmodel.Typed;
import org.jackie.jmodel.FQNamed;

/**
 * @author Patrik Beno
 */
public interface JArray extends JType {

	JType getComponentType();

	int getDimensions();

}
