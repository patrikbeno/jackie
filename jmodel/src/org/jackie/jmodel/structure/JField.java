package org.jackie.jmodel.structure;

import org.jackie.jmodel.JClass;
import org.jackie.jmodel.props.AccessMode;
import org.jackie.jmodel.props.Accessible;
import org.jackie.jmodel.props.Flag;

/**
 * @author Patrik Beno
 */
public interface JField extends JVariable<JClass>, Accessible {

	JClass getJClass();

	Editor edit();

	public interface Editor extends JVariable.Editor<JField, JClass> {

		Editor setScope(JClass node);

		Editor setName(String name);

		Editor setType(JClass type);

		Editor setAccessMode(AccessMode accessMode);

		Editor setFlags(Flag... flags);

		JField editable();
	}

}
