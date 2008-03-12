package org.jackie.compiler.jmodelimpl.structure;

import org.jackie.jmodel.structure.JMethod;
import org.jackie.jmodel.structure.JParameter;
import org.jackie.jmodel.structure.JVariable;
import org.jackie.jmodel.JClass;
import org.jackie.utils.Assert;

/**
 * @author Patrik Beno
 */
public class JParameterImpl extends JVariableImpl<JMethod> implements JParameter {

	public boolean isEditable() {
		return scope().isEditable();
	}

	public Editor edit() {
		return new Editor();
	}

	public class Editor implements JParameter.Editor {

		final JParameterImpl pthis = JParameterImpl.this;

		public Editor setScope(JMethod node) {
			pthis.scope = node;
			return this;
		}

		public Editor setName(String name) {
			pthis.name = name;
			return this;
		}

		public Editor setType(JClass type) {
			pthis.type = type;
			return this;
		}

		public JParameter editable() {
			return JParameterImpl.this;
		}
	}

}
