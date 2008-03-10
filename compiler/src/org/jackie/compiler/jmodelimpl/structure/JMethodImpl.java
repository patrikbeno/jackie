package org.jackie.compiler.jmodelimpl.structure;

import static org.jackie.compiler.util.Helper.assertEditable;
import org.jackie.compiler.jmodelimpl.FlagsImpl;
import org.jackie.jmodel.JClass;
import org.jackie.jmodel.attribute.Attributes;
import org.jackie.jmodel.code.CodeBlock;
import org.jackie.jmodel.extension.Extensions;
import org.jackie.jmodel.props.AccessMode;
import org.jackie.jmodel.props.Flag;
import org.jackie.jmodel.props.Flags;
import org.jackie.jmodel.structure.JMethod;
import org.jackie.jmodel.structure.JParameter;
import org.jackie.jmodel.structure.JVariable;
import org.jackie.utils.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class JMethodImpl implements JMethod {

	protected String name;
	protected JClass type;

	protected AccessMode accessMode;
	protected Flags flags;

	protected Attributes attributes;
	protected Extensions extensions;

	protected JClass jclass;
	protected List<JParameter> parameters;
	protected List<JClass> exceptions;
	protected List<JVariable> locals;
    
	protected CodeBlock code;


	public String getName() {
		return name;
	}

	public JClass getType() {
		return type;
	}

	public Attributes attributes() {
		return attributes;
	}

	public Extensions extensions() {
		return extensions;
	}

	///

	public JClass getJClass() {
		return jclass;
	}

	public List<JParameter> getParameters() {
		return Collections.unmodifiableList(parameters);
	}

	public List<JClass> getExceptions() {
		return Collections.unmodifiableList(exceptions);
	}

	public List<JVariable> getLocalVariables() {
		return Collections.unmodifiableList(locals);
	}

	public CodeBlock getCodeBlock() {
		return code;
	}

	public Flags flags() {
		if (flags == null) {
			flags = new FlagsImpl();
		}
		return flags;
	}

	public Editor edit() {
		assertEditable();
		return new Editor() {

			final JMethodImpl mthis = JMethodImpl.this;

			public Editor setName(String name) {
				mthis.name = name;
				return this;
			}

			public Editor setType(JClass type) {
				mthis.type = type;
				return this;
			}

			public Editor setParameters(List<JParameter> parameters) {
				mthis.parameters = new ArrayList<JParameter>(parameters);
				return this;
			}

			public Editor setExceptions(List<JClass> exceptions) {
				mthis.exceptions = new ArrayList<JClass>(exceptions);
				return this;
			}

			public Editor setAccessMode(AccessMode accessMode) {
				mthis.accessMode = accessMode;
				return this;
			}

			public Editor setFlags(Flag ... flags) {
				mthis.flags().edit().reset().set(flags);
				return this;
			}

			public JMethod editable() {
				return JMethodImpl.this;
			}

		};
	}
}
