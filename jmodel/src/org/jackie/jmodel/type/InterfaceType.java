package org.jackie.jmodel.type;

import org.jackie.jmodel.SpecialType;
import org.jackie.jmodel.structure.JField;
import org.jackie.jmodel.structure.JMethod;

import java.util.List;

/**
 * @author Patrik Beno
 */
public interface InterfaceType extends SpecialType {

	List<JField> getConstants();

	List<JMethod> getInterfaceMethods();

}
