package org.jackie.jmodel.type;

import org.jackie.jmodel.JType;
import org.jackie.jmodel.Annotated;
import org.jackie.jmodel.JPackage;
import org.jackie.jmodel.FQNamed;
import org.jackie.jmodel.structure.JField;
import org.jackie.jmodel.structure.JMethod;
import org.jackie.jmodel.structure.JTypeVariable;

import javax.lang.model.element.NestingKind;
import java.util.List;

/**
 * @author Patrik Beno
 */
public interface JClass extends JType, FQNamed, Annotated {

	/// nesting

	NestingKind getNestingKind();

	JClass getEnclosingClass();

	JMethod getEnclosingMethod();


	/// type info

	JPackage getJPackage();

	JClass getSuperClass();

	JReferenceType getGenericSuperClass();

	List<JClass> getInterfaces();

	List<JReferenceType> getGenericInterfaces();

	List<JTypeVariable> getTypeVariables();


	List<JField> getFields();

	List<? extends JMethod> getMethods();


	/// view methods ///

	InterfaceType asJInterface();

	EnumType asJEnum();

	AnnotationType asAnnotationType();
	

	// todo shortcuts: class initializers, constructors, declared methods, field accessors (?)

	// todo hierarchy support (?): scope-visible methods in hierarchy

}
