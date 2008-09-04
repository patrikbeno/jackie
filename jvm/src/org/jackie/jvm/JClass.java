package org.jackie.jvm;

import org.jackie.jvm.attribute.Attributed;
import org.jackie.jvm.extension.Extensible;
import org.jackie.jvm.props.AccessMode;
import org.jackie.jvm.props.Accessible;
import org.jackie.jvm.props.FQNamed;
import org.jackie.jvm.props.Flag;
import org.jackie.jvm.props.Flagged;
import org.jackie.jvm.props.Named;
import org.jackie.jvm.structure.JField;
import org.jackie.jvm.structure.JMethod;

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


	// helpers

	boolean isAssignableFrom(JClass jclass);
	boolean isAssignableFrom(Class cls);

	boolean isInstance(JClass jclass);
	boolean isInstance(Class cls);

	public interface Editor extends org.jackie.jvm.Editor<JClass> {

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
