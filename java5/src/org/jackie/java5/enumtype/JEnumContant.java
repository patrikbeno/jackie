package org.jackie.java5.enumtype;

import org.jackie.jvm.props.Named;
import org.jackie.jvm.structure.JField;

/**
 * @author Patrik Beno
 */
public interface JEnumContant extends Named {

	JField getField();

	String getName();

	int ordinal();

}
