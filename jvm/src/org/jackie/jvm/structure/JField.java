package org.jackie.jvm.structure;

import org.jackie.jvm.JClass;
import org.jackie.jvm.props.AccessMode;
import org.jackie.jvm.props.Accessible;
import org.jackie.jvm.props.Flag;

/**
 * @author Patrik Beno
 */
public interface JField extends JVariable<JClass>, Accessible {

	JClass getJClass();

	Editor edit();

	public interface Editor extends JVariable.Editor<JField, JClass> {

		Editor setName(String name);

		Editor setType(JClass type);

		Editor setAccessMode(AccessMode accessMode);

		Editor setFlags(Flag... flags);

		JField editable();
	}

}
