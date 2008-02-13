package org.jackie.jmodel.type;

import org.jackie.jmodel.SpecialType;
import org.jackie.jmodel.structure.JConstructor;
import org.jackie.jmodel.structure.JMethod;

import java.util.List;

/**
 * @author Patrik Beno
 */
public interface ClassType extends SpecialType {

	List<JConstructor> getConstructors();

	List<JMethod> getMethods();

	// get public constructor/methods?
}
