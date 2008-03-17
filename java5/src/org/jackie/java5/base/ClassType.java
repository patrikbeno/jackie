package org.jackie.java5.base;

import org.jackie.jvm.Editable;
import org.jackie.jvm.JClass;
import org.jackie.jvm.extension.Extension;
import org.jackie.jvm.structure.JMethod;

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


	public static interface Editor extends org.jackie.jvm.Editor<ClassType> {

		void addConstructor(JMethod ctor);

	}
}
