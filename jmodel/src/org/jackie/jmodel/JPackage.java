package org.jackie.jmodel;

/**
 * @author Patrik Beno
 */
public interface JPackage extends JType, FQNamed {

	JPackage getParentPackage();

}
