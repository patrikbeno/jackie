package org.jackie.java5.enumtype;

import org.jackie.jmodel.props.Named;
import org.jackie.jmodel.structure.JField;

/**
 * @author Patrik Beno
 */
public interface JEnumContant extends Named {

	JField getField();

	String getName();

	int ordinal();

}
