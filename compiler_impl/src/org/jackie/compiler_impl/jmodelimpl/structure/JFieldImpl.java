package org.jackie.compiler_impl.jmodelimpl.structure;

import org.jackie.compiler.spi.Compilable;
import org.jackie.compiler.spi.CompilableHelper;
import org.jackie.compiler_impl.bytecode.ByteCodeBuilder;
import org.jackie.jvm.JClass;
import org.jackie.jvm.props.AccessMode;
import org.jackie.jvm.props.Flag;
import org.jackie.jvm.structure.JField;
import org.jackie.jvm.structure.JMethod;
import org.jackie.context.ContextManager;
import org.jackie.context.ContextRunner;
import org.objectweb.asm.FieldVisitor;

/**
 * @author Patrik Beno
 */
public class JFieldImpl extends JVariableImpl<JClass> implements JField, Compilable {

	protected AccessMode accessMode;

	public JFieldImpl(JClass owner) {
		super(owner);
	}

	public AccessMode getAccessMode() {
		return accessMode;
	}

	public JClass getJClass() {
		return scope(); 
	}

	public JField.Editor edit() {
		return new Editor();
	}

	class Editor implements JField.Editor  {

		final JFieldImpl fthis = JFieldImpl.this;

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

	public void compile() {
        ByteCodeBuilder.execute(new ByteCodeBuilder() {

			JField fthis = JFieldImpl.this;

			protected void run() {
				final FieldVisitor fv = cv().visitField(
						toAccessFlag(getAccessMode()),
						getName(), bcDesc(fthis),
						null, // todo field signature
						null);
				CompilableHelper.compile(fthis.attributes());
				CompilableHelper.compile(fthis.extensions());
				fv.visitEnd();
			}
		});
	}
}
