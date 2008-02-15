package org.jackie.jmodel.structure;

import org.jackie.jmodel.props.Named;

/**
 * @author Patrik Beno
 */
public interface JEnumContant extends Named {

	JField getField();

	String getName();

	int ordinal();

}
