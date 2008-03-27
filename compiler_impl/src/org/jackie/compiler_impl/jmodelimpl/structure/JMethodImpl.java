package org.jackie.compiler_impl.jmodelimpl.structure;

import static org.jackie.compiler_impl.util.Helper.assertEditable;
import org.jackie.compiler_impl.jmodelimpl.FlagsImpl;
import org.jackie.compiler_impl.jmodelimpl.ExtensionsImpl;
import org.jackie.compiler_impl.jmodelimpl.attribute.AttributesImpl;
import org.jackie.jvm.JClass;
import org.jackie.jvm.attribute.Attributes;
import org.jackie.jvm.code.CodeBlock;
import org.jackie.jvm.extension.Extensions;
import org.jackie.jvm.props.AccessMode;
import org.jackie.jvm.props.Flag;
import org.jackie.jvm.props.Flags;
import org.jackie.jvm.structure.JMethod;
import org.jackie.jvm.structure.JParameter;
import org.jackie.jvm.structure.JVariable;

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

	public JMethodImpl(JClass jclass) {
		this.jclass = jclass;
	}


	public String getName() {
		return name;
	}

	public JClass getType() {
		return type;
	}

	public Attributes attributes() {
		if (attributes == null) {
			attributes = new AttributesImpl();
		}
		return attributes;
	}

	public Extensions extensions() {
		if (extensions == null) {
			extensions = new ExtensionsImpl(this);
		}
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

	public boolean isEditable() {
		return getJClass().isEditable();
	}

	public Editor edit() {
		assertEditable(getJClass());
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