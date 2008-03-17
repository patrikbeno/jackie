package org.jackie.jvm.structure;

import org.jackie.jvm.JClass;

/**
 * @author Patrik Beno
 */
public interface JParameter extends JVariable<JMethod> {

	Editor edit();

	public interface Editor extends JVariable.Editor<JParameter,JMethod> {

		Editor setScope(JMethod node);

		Editor setName(String name);

		Editor setType(JClass type);

		JParameter editable();
	}

}

