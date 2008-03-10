package org.jackie.jmodel.structure;

import org.jackie.jmodel.JClass;
import org.jackie.jmodel.props.AccessMode;
import org.jackie.jmodel.props.Flag;

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

