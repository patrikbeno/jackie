package org.jackie.jmodel.type;

import org.jackie.jmodel.JType;
import org.jackie.jmodel.FQNamed;
import org.jackie.jmodel.structure.JField;
import org.jackie.jmodel.structure.JEnumContant;

import java.util.List;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public interface EnumType extends ExtendedType {

	Set<String> getEnumConstantNames();

	JEnumContant getEnumConstant(String name);

	JEnumContant getEnumConstant(int ordinal);

	List<JEnumContant> getEnumContants(); 

}
