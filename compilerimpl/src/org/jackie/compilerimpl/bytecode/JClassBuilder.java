package org.jackie.compilerimpl.bytecode;

import org.jackie.compiler.event.JClassEvents;
import org.jackie.compiler.event.ExtensionEvents;
import org.jackie.compilerimpl.jmodelimpl.JClassImpl;
import org.jackie.compilerimpl.jmodelimpl.LoadLevel;
import org.jackie.compilerimpl.jmodelimpl.AccessModeHelper;
import org.jackie.compilerimpl.jmodelimpl.FlagsHelper;
import static org.jackie.compilerimpl.jmodelimpl.AccessModeHelper.toAccessMode;
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
import org.jackie.jvm.props.JFlags.Editor;
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

		jclass.edit().setAccessMode(toAccessMode(classfile.flags()));
		FlagsHelper.toJFlags(classfile.flags(), jclass.flags());

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

		buildAttributes(classfile.attributes(), jclass.attributes());

		((JClassImpl)jclass).loadLevel = LoadLevel.CODE;

		events(JClassEvents.class).loaded(jclass);
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
					jparams.add(p);
				}
				jmethod.edit().setParameters(jparams);

				jmethod.edit().setAccessMode(toAccessMode(m.flags()));
				FlagsHelper.toJFlags(m.flags(), jmethod.flags());

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
		a.detach();
		dst.edit().addAttribute(new GenericAttribute(dst.jnode(), a.name(), a));
	}

}
