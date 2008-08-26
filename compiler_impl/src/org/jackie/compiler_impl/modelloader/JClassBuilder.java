package org.jackie.compiler_impl.modelloader;

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
import org.jackie.jvm.JClass;
import org.jackie.jvm.attribute.Attributes;
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

	void buildField(FieldInfo f) {
		TypeDescriptor desc = f.typeDescriptor();

		JField jfield = new JFieldImpl(jclass);
		jfield.edit()
				.setName(f.name())
				.setType(getJClass(new ClassName(desc.getTypeName(), desc.getDimensions())));

		jclass.edit().addField(jfield);
	}

	void buildMethod(MethodInfo m) {
		MethodDescriptor desc = m.methodDescriptor();

		JMethod jmethod = new JMethodImpl(jclass);
		jmethod.edit()
				.setName(m.name())
				.setType(getJClass(new ClassName(desc.getReturnType().getTypeName(), desc.getReturnType().getDimensions())));

		List<JParameter> jparams = new ArrayList<JParameter>();
		for (TypeDescriptor d : desc.getParameterTypes()) {
			JParameter p = new JParameterImpl(jmethod);
			p.edit().setType(getJClass(new ClassName(d.getTypeName(), d.getDimensions())));
		}
		jmethod.edit().setParameters(jparams);

		jclass.edit().addMethod(jmethod);
		buildAttributes(m.attributes(), jmethod.attributes());
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
