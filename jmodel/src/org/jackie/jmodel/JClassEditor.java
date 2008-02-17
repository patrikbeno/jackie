package org.jackie.jmodel;

import org.jackie.jmodel.structure.JField;
import org.jackie.jmodel.structure.JMethod;

/**
 * @author Patrik Beno
 */
public interface JClassEditor extends JNodeEditor<JClass> {

	/// naming & hierarchy ///

	void setName(String name);

	void setPackage(JPackage jpackage);

	void setSuperClass(JClass jclass);

	void setInterfaces(JClass ... ifaces);

	void addInterface(JClass iface);

	void setEnclosingClass(JClass jclass);

	void setEnclosingMethod(JMethod jmethod);

	/// structure ///

	void addField(JField jfield);

	void addMethod(JMethod jmethod);

}
