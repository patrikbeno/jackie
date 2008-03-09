package org.jackie.compiler.jmodelimpl.structure;

import org.jackie.jmodel.structure.JMethod;
import org.jackie.jmodel.structure.JParameter;

/**
 * @author Patrik Beno
 */
public class JParameterImpl extends JVariableImpl<JParameter,JParameter.Editor,JMethod> implements JParameter {
	public class Editor extends EditorImpl<JParameter,JParameter.Editor,JMethod> {
	}
}
