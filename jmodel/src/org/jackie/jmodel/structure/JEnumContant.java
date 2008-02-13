package org.jackie.jmodel.structure;

import org.jackie.jmodel.props.Named;
import org.jackie.jmodel.props.Typed;
import org.jackie.jmodel.props.Annotated;
import org.jackie.jmodel.type.EnumType;
import org.jackie.jmodel.JClass;

/**
 * @author Patrik Beno
 */
public interface JEnumContant extends Named {

	JField getField();

	String getName();

	int ordinal();

}
