package org.jackie.jmodel.structure;

import org.jackie.jmodel.JClass;
import org.jackie.jmodel.props.AccessMode;
import org.jackie.jmodel.props.Accessible;
import org.jackie.jmodel.props.Flag;

/**
 * @author Patrik Beno
 */
public interface JField extends _JField<JField,JField.Editor,JClass> {

	public interface Editor extends _JField.Editor<JField,Editor,JClass> {
	}

}

interface _JField<V extends JVariable<V,E,N>, E extends _JField.Editor<V,E,N>, N extends JClass>
		extends JVariable<V,E,N>, Accessible
{
	interface Editor<V extends JVariable<V,E,N>, E extends Editor<V,E,N>, N extends JClass>
			extends JVariable.Editor<V,E,N>
	{

		E setAccessMode(AccessMode accessMode);

		E setFlags(Flag... flags);

	}
}





