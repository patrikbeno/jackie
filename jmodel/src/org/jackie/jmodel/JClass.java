package org.jackie.jmodel;

import org.jackie.jmodel.props.Annotated;
import org.jackie.jmodel.props.FQNamed;
import org.jackie.jmodel.props.Named;
import org.jackie.jmodel.structure.JField;
import org.jackie.jmodel.structure.JMethod;
import org.jackie.jmodel.structure.JTypeVariable;
import org.jackie.jmodel.structure.ReferenceType;

import javax.lang.model.element.NestingKind;
import java.util.List;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public interface JClass extends FQNamed, Annotated, JNode, Named {

	Set<Class<? extends SpecialType>> getSpecialTypeCapabilities();

	<T extends SpecialType> boolean isSpecialType(Class<T> type);

	<T extends SpecialType> T getSpecialTypeView(Class<T> type);

	/// nesting

	NestingKind getNestingKind();

	JClass getEnclosingClass();

	JMethod getEnclosingMethod();

	Set<JClass> getNestedClasses();


	/// type info

	String getName();

	String getFQName();

	JPackage getJPackage();

	JClass getSuperClass();

	ReferenceType getGenericSuperClass();

	List<JClass> getInterfaces();

	List<ReferenceType> getGenericInterfaces();

	List<JTypeVariable> getTypeVariables();


	List<JField> getFields();

	List<? extends JMethod> getMethods();

}
