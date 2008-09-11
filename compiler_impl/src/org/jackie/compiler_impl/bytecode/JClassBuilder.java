package org.jackie.compiler_impl.bytecode;

import org.jackie.compiler_impl.jmodelimpl.JClassImpl;
import org.jackie.compiler_impl.jmodelimpl.LoadLevel;
import org.jackie.compiler_impl.jmodelimpl.attribute.GenericAttribute;
import org.jackie.compiler_impl.jmodelimpl.structure.JFieldImpl;
import org.jackie.compiler_impl.jmodelimpl.structure.JMethodImpl;
import org.jackie.compiler_impl.jmodelimpl.structure.JParameterImpl;
import org.jackie.jclassfile.constantpool.impl.ClassRef;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.model.ClassFile;
import org.jackie.jclassfile.model.FieldInfo;
import org.jackie.jclassfile.model.MethodInfo;
import org.jackie.jclassfile.util.MethodDescriptor;
import org.jackie.jclassfile.util.TypeDescriptor;
import org.jackie.jclassfile.flags.Flags;
import org.jackie.jclassfile.flags.Access;
import org.jackie.jvm.JClass;
import org.jackie.jvm.attribute.Attributes;
import org.jackie.jvm.attribute.special.KindAttribute;
import org.jackie.jvm.attribute.special.Kind;
import org.jackie.jvm.structure.JField;
import org.jackie.jvm.structure.JMethod;
import org.jackie.jvm.structure.JParameter;
import org.jackie.utils.ClassName;
import org.jackie.utils.Assert;

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

		jclass.attributes().edit().addAttribute(new KindAttribute(jclass, getKind()));

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
	}

	Kind getKind() {
		Flags flags = classfile.flags();
		if (flags.isSet(Access.ANNOTATION)) {
			return Kind.ANNOTATION;
		} else if (flags.isSet(Access.ENUM)) {
			return Kind.ENUM;
		} else if (flags.isSet(Access.INTERFACE)) {
			return Kind.INTERFACE;
		} else {
			return Kind.CLASS;
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
