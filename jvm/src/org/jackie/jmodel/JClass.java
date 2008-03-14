package org.jackie.jmodel;

import org.jackie.jmodel.attribute.Attributed;
import org.jackie.jmodel.props.AccessMode;
import org.jackie.jmodel.props.Accessible;
import org.jackie.jmodel.props.FQNamed;
import org.jackie.jmodel.props.Flag;
import org.jackie.jmodel.props.Flagged;
import org.jackie.jmodel.props.Named;
import org.jackie.jmodel.extension.Extensible;
import org.jackie.jmodel.structure.JField;
import org.jackie.jmodel.structure.JMethod;

import java.util.List;

/**
 * @author Patrik Beno
 */
public interface JClass extends JNode, Named, FQNamed, Accessible, Flagged, Attributed, Extensible, Editable<JClass.Editor> {


	/// typedef


	String getName();

	String getFQName();

	JPackage getJPackage();

	JClass getSuperClass();

	List<JClass> getInterfaces();


	/// structure
	

	List<JField> getFields();

	List<? extends JMethod> getMethods();


	public interface Editor extends org.jackie.jmodel.Editor<JClass> {

		Editor setName(String name);

		Editor setPackage(JPackage jpackage);

		Editor setSuperClass(JClass jclass);

		Editor setInterfaces(JClass ... ifaces);

		Editor addInterface(JClass iface);

		Editor setAccessMode(AccessMode accessMode);

		Editor setFlags(Flag ... flags);

		/// structure ///


		Editor addField(JField jfield);

		Editor addMethod(JMethod jmethod);

	}
}
