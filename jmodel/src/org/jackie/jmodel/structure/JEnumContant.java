package org.jackie.jmodel.structure;

import org.jackie.jmodel.Named;
import org.jackie.jmodel.type.EnumType;

/**
 * @author Patrik Beno
 */
public interface JEnumContant extends Named {

	EnumType getJEnum();

	String getName();

	int ordinal();
}
