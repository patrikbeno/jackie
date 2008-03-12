package org.jackie.compiler.jmodelimpl.structure;

import org.jackie.jmodel.JClass;
import org.jackie.jmodel.JNode;
import org.jackie.jmodel.props.AccessMode;
import org.jackie.jmodel.props.Flag;
import org.jackie.jmodel.structure.JField;
import org.jackie.jmodel.structure.JVariable;
import org.jackie.utils.Assert;

/**
 * @author Patrik Beno
 */
public class JFieldImpl extends JVariableImpl<JClass> implements JField {

	protected AccessMode accessMode;

	public AccessMode getAccessMode() {
		return accessMode;
	}

	public JClass getJClass() {
		return scope(); 
	}

	public boolean isEditable() {
		return getJClass().isEditable();
	}

	public JField.Editor edit() {
		return new Editor();
	}

	class Editor implements JField.Editor  {

		final JFieldImpl fthis = JFieldImpl.this;

		public Editor setScope(JClass node) {
			fthis.scope = node;
			return this;
		}

		public Editor setName(String name) {
			fthis.name = name;
			return this;
		}

		public Editor setType(JClass type) {
			fthis.type = type;
			return this;
		}

		public Editor setAccessMode(AccessMode accessMode) {
			fthis.accessMode = accessMode;
			return this;
		}

		public Editor setFlags(Flag... flags) {
			fthis.flags().edit().set(flags);
			return this;
		}

		public JField editable() {
			return fthis;
		}
	}
}
