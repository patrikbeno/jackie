package org.jackie.compiler.jmodelimpl.structure;

import org.jackie.jmodel.JClass;
import org.jackie.jmodel.props.AccessMode;
import org.jackie.jmodel.props.Flag;
import org.jackie.jmodel.structure.JField;
import org.jackie.utils.Assert;

/**
 * @author Patrik Beno
 */
public class JFieldImpl extends JVariableImpl<JField,JField.Editor, JClass> implements JField{

	public AccessMode getAccessMode() {
		throw Assert.notYetImplemented(); // todo implement this 
	}

	public class Editor extends EditorImpl<JField,JField.Editor,JClass> implements JField.Editor {
		public JField.Editor setAccessMode(AccessMode accessMode) {
			throw Assert.notYetImplemented(); // todo implement this
		}

		public JField.Editor setFlags(Flag... flags) {
			throw Assert.notYetImplemented(); // todo implement this
		}
	}
}
