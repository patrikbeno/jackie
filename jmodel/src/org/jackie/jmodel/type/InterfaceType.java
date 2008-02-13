package org.jackie.jmodel.type;

import org.jackie.jmodel.SpecialType;
import org.jackie.jmodel.structure.JMethod;
import org.jackie.jmodel.structure.JField;

import java.util.List;

/**
 * @author Patrik Beno
 */
public interface InterfaceType extends SpecialType {

	List<JField> getConstants();

	List<JMethod> getInterfaceMethods();

}
