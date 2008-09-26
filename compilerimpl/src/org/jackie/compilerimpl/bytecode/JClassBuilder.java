package org.jackie.compilerimpl.bytecode;

import org.jackie.compiler.event.JClassEvents;
import org.jackie.compiler.event.ExtensionEvents;
import org.jackie.compilerimpl.jmodelimpl.JClassImpl;
import org.jackie.compilerimpl.jmodelimpl.LoadLevel;
import org.jackie.compilerimpl.jmodelimpl.attribute.GenericAttribute;
import org.jackie.compilerimpl.jmodelimpl.structure.JFieldImpl;
import org.jackie.compilerimpl.jmodelimpl.structure.JMethodImpl;
import org.jackie.compilerimpl.jmodelimpl.structure.JParameterImpl;
import static org.jackie.event.Events.events;
import org.jackie.jclassfile.constantpool.impl.ClassRef;
import org.jackie.jclassfile.flags.Access;
import org.jackie.jclassfile.flags.Flags;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.model.ClassFile;
import org.jackie.jclassfile.model.FieldInfo;
import org.jackie.jclassfile.model.MethodInfo;
import org.jackie.jclassfile.util.MethodDescriptor;
import org.jackie.jclassfile.util.TypeDescriptor;
import org.jackie.jvm.JClass;
import org.jackie.jvm.attribute.Attributes;
import org.jackie.jvm.props.AccessMode;
import org.jackie.jvm.props.Flag;
import org.jackie.jvm.props.Flags.Editor;
import org.jackie.jvm.structure.JField;
import org.jackie.jvm.structure.JMethod;
import org.jackie.jvm.structure.JParameter;
import org.jackie.utils.ClassName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class JClassBuilder extends AbstractBuilder {

	ClassFile classfile;

	JClass jclass;

	public JClassBuilder(ClassFile classfile) {
		this.classfile = classfile;
	}

	public void build() {
		ClassName clsname = getClassNameFromBinaryName(classfile.classname());
		jclass = getJClass(clsname);

		events(JClassEvents.class).loading(jclass);
		events(ExtensionEvents.class).resolveClassFlags(classfile.flags(), jclass);

		resolveAccessMode();
		resolveFlags();

		if (classfile.superclass() != null) {
			ClassName cname = getClassNameFromBinaryName(classfile.superclass());
			jclass.edit().setSuperClass(getJClass(cname));
		}

		for (ClassRef cref : classfile.interfaces()) {
			ClassName cname = getClassNameFromBinaryName(cref.value());
			jclass.edit().addInterface(getJClass(cname));
		}

		for (FieldInfo f : classfile.fields()) {
			buildField(f);
		}

		for (MethodInfo m : classfile.methods()) {
			buildMethod(m);
		}

		for (AttributeInfo a : classfile.attributes()) {
			buildAttribute(a, jclass.attributes());
		}

		((JClassImpl)jclass).loadLevel = LoadLevel.CODE;

		events(JClassEvents.class).loaded(jclass);
	}

	private void resolveAccessMode() {
		Flags flags = classfile.flags();
		if (flags.isSet(Access.PUBLIC)) {
			jclass.edit().setAccessMode(AccessMode.PUBLIC);
		} else if (flags.isSet(Access.PROTECTED)) {
			jclass.edit().setAccessMode(AccessMode.PROTECTED);
		} else if (flags.isSet(Access.PRIVATE)) {
			jclass.edit().setAccessMode(AccessMode.PRIVATE);
		} else {
			jclass.edit().setAccessMode(AccessMode.PACKAGE);
		}
	}

	private void resolveFlags() {
		Flags flags = classfile.flags();
		Editor jflags = jclass.flags().edit();

		if (flags.isSet(Access.ABSTRACT)) {
			jflags.add(Flag.ABSTRACT);
		}
		if (flags.isSet(Access.STATIC)) {
			jflags.add(Flag.STATIC);
		}
		if (flags.isSet(Access.FINAL)) {
			jflags.add(Flag.FINAL);
		}
		if (flags.isSet(Access.SYNTHETIC)) {
			jflags.add(Flag.SYNTHETIC);
		}
	}

	void buildField(final FieldInfo f) {
		ByteCodeLoader.execute(new ByteCodeLoader() {
			protected void run() {
				JField jfield = new JFieldImpl(jclass);
				jfield.edit()
						.setName(f.name())
						.setType(getJClass(f.typeDescriptor()));

				jclass.edit().addField(jfield);
			}
		});
	}

	void buildMethod(final MethodInfo m) {
		ByteCodeLoader.execute(new ByteCodeLoader() {
			protected void run() {
				MethodDescriptor desc = m.methodDescriptor();

				JMethod jmethod = new JMethodImpl(jclass);
				jmethod.edit()
						.setName(m.name())
						.setType(getJClass(m.methodDescriptor().getReturnType()));

				List<JParameter> jparams = new ArrayList<JParameter>();
				for (TypeDescriptor d : desc.getParameterTypes()) {
					JParameter p = new JParameterImpl(jmethod);
					p.edit().setType(getJClass(d));
				}
				jmethod.edit().setParameters(jparams);

				jclass.edit().addMethod(jmethod);
				buildAttributes(m.attributes(), jmethod.attributes());
			}
		});
	}

	void buildAttributes(List<AttributeInfo> src, Attributes dst) {
		for (AttributeInfo a : src) {
			buildAttribute(a, dst);
		}
	}

	void buildAttribute(AttributeInfo a, Attributes dst) {
		dst.edit().addAttribute(new GenericAttribute(dst.jnode(), a.name(), a));
	}

}
