package org.jackie.jmodel.structure;

import org.jackie.jmodel.props.AccessMode;
import org.jackie.jmodel.props.Flag;
import org.jackie.jmodel.JClass;

/**
 * @author Patrik Beno
 */
public class ApiTest {

	JParameter jparam;
	JField jfield;
	JMethod jmethod;
	JClass jclass;


	void jfield() {

		JMethod s1 = jparam.scope();
		JParameter.Editor e1 = jparam.edit();

		JClass s2 = jfield.scope();
		JField.Editor e2 = jfield.edit();

		JParameter.Editor e3 = jparam.edit()
				.setName("")
				.setType(null)
				.setScope(null);

		jfield.edit()
				.setName("foo")
				.setFlags(Flag.FINAL)
				.editable()
				.edit()
				.setName("bar")
				.setScope(jclass)
				.setAccessMode(AccessMode.PACKAGE)
				.editable()
				.edit()
				.setType(null)
				.setFlags(Flag.values());


	}

}

