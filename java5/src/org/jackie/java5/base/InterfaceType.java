package org.jackie.java5.base;

import org.jackie.jvm.Editable;
import org.jackie.jvm.extension.Extension;
import org.jackie.jvm.structure.JField;
import org.jackie.jvm.structure.JMethod;

import java.util.List;

/**
 * @author Patrik Beno
 */
public interface InterfaceType extends Extension, Editable<InterfaceType.Editor> {

	List<JField> getConstants();

	List<JMethod> getInterfaceMethods();


	public interface Editor extends org.jackie.jvm.Editor<InterfaceType> {

	}

}
