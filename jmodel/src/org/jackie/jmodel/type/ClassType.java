package org.jackie.jmodel.type;

import org.jackie.jmodel.ClassNestingLevel;
import org.jackie.jmodel.JClass;
import org.jackie.jmodel.SpecialType;
import org.jackie.jmodel.structure.JConstructor;
import org.jackie.jmodel.structure.JMethod;

import java.util.List;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public interface ClassType extends SpecialType {

	/// nesting

	ClassNestingLevel getNestingKind();

	JClass getEnclosingClass();

	JMethod getEnclosingMethod();

	Set<JClass> getNestedClasses();


	///


	List<JConstructor> getConstructors();

	List<JMethod> getMethods();

}
