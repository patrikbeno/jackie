package org.jackie.java5.base;

import org.jackie.jmodel.extension.Extension;
import org.jackie.jmodel.structure.JField;
import org.jackie.jmodel.structure.JMethod;
import org.jackie.jmodel.Editable;
import org.jackie.jmodel.JClass;

import java.util.List;

/**
 * @author Patrik Beno
 */
public interface InterfaceType extends Extension<JClass>, Editable<InterfaceType.Editor> {

	List<JField> getConstants();

	List<JMethod> getInterfaceMethods();


	public interface Editor extends org.jackie.jmodel.Editor<InterfaceType> {

	}

}
