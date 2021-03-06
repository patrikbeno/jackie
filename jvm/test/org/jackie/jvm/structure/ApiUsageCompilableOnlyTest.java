package org.jackie.jvm.structure;

import org.jackie.jvm.JClass;
import org.jackie.jvm.props.AccessMode;
import org.jackie.jvm.props.Flag;

/**
 * @author Patrik Beno
 */
public class ApiUsageCompilableOnlyTest {

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
				.setType(null);

		jfield.edit()
				.setName("foo")
				.setFlags(Flag.FINAL)
				.editable()
				.edit()
				.setName("bar")
				.setAccessMode(AccessMode.PACKAGE)
				.editable()
				.edit()
				.setType(null)
				.setFlags(Flag.values());


	}

}

