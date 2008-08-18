package org.jackie.compiler_impl.jmodelimpl.structure;

import org.jackie.jvm.JClass;
import org.jackie.jvm.JNode;
import org.jackie.jvm.structure.JMethod;
import org.jackie.jvm.structure.JParameter;

/**
 * @author Patrik Beno
 */
public class JParameterImpl extends JVariableImpl<JMethod> implements JParameter {

	public JParameterImpl(JNode owner) {
		super(owner);
	}

	public boolean isEditable() {
		return scope().isEditable();
	}

	public Editor edit() {
		return new Editor();
	}

	public class Editor implements JParameter.Editor {

		final JParameterImpl pthis = JParameterImpl.this;

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
