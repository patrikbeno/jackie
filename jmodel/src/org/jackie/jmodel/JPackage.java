package org.jackie.jmodel;

import org.jackie.jmodel.props.FQNamed;
import org.jackie.jmodel.props.Named;

import java.util.Set;

/**
 * @author Patrik Beno
 */
public interface JPackage extends FQNamed, JNode, Named {

	JPackage getParentPackage();

	Set<JPackage> getSubPackages();

	Set<JClass> getClasses();

}
