package org.jackie.compiler_impl.jmodelimpl.structure;

import org.jackie.compiler.spi.Compilable;
import org.jackie.compiler_impl.bytecode.ByteCodeBuilder;
import org.jackie.jvm.JClass;
import org.jackie.jvm.props.AccessMode;
import org.jackie.jvm.props.Flag;
import org.jackie.jvm.structure.JField;
import org.jackie.utils.Assert;
import org.jackie.jclassfile.model.ClassFile;
import org.jackie.jclassfile.model.FieldInfo;
import org.jackie.jclassfile.util.TypeDescriptor;

/**
 * @author Patrik Beno
 */
public class JFieldImpl extends JVariableImpl<JClass> implements JField {

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

	public void compile(final ClassFile classfile) {
		ByteCodeBuilder.execute(new ByteCodeBuilder() {
			protected void run() {
				FieldInfo f = new FieldInfo(classfile);
				f.name(getName());
				f.typeDescriptor(getTypeDescriptor(getType()));

				classfile.addField(f);
			}
		});
	}

}
