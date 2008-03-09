package org.jackie.jmodel.extension.base;

import org.jackie.jmodel.Editable;
import org.jackie.jmodel.JClass;
import org.jackie.jmodel.Editor;
import org.jackie.jmodel.extension.Extension;
import org.jackie.jmodel.structure.JMethod;

import java.util.List;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public interface ClassType extends Extension<JClass>, Editable<ClassType.Editor> {

	/// nesting

	ClassNestingLevel getNestingKind();

	JClass getEnclosingClass();

	JMethod getEnclosingMethod();

	Set<JClass> getNestedClasses();


	///


	List<JMethod> getConstructors();

	List<JMethod> getMethods();


	public static interface Editor extends org.jackie.jmodel.Editor<ClassType> {

		void addConstructor(JMethod ctor);

	}
}
