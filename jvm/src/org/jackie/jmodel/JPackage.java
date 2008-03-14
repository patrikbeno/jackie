package org.jackie.jmodel;

import org.jackie.jmodel.props.FQNamed;
import org.jackie.jmodel.props.Named;

import java.util.Set;

/**
 * @author Patrik Beno
 */
public interface JPackage extends JNode, Named, FQNamed, Editable<JPackage.Editor> {

	boolean isDefault();

	JPackage getParentPackage();

	Set<JPackage> getSubPackages();

	Set<JClass> getClasses();

	public interface Editor extends org.jackie.jmodel.Editor<JPackage> {

		Editor setName(String name);

		Editor setParent(JPackage parent);

		Editor addClass(JClass jclass);

	}

}
