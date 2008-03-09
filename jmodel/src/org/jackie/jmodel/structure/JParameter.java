package org.jackie.jmodel.structure;

import org.jackie.jmodel.JClass;
import org.jackie.jmodel.props.AccessMode;
import org.jackie.jmodel.props.Flag;

/**
 * @author Patrik Beno
 */
public interface JParameter extends _JParameter<JParameter,JParameter.Editor,JMethod> {

	public interface Editor extends JVariable.Editor<JParameter,Editor,JMethod> {
	}

}

interface _JParameter<V extends JVariable<V,E,N>, E extends JVariable.Editor<V,E,N>, N extends JMethod>
		extends JVariable<V,E,N>
{
}
