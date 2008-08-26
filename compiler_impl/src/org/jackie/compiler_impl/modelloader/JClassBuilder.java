package org.jackie.compiler_impl.modelloader;

import org.jackie.jvm.JClass;
import org.jackie.jclassfile.model.ClassFile;
import org.jackie.jclassfile.model.FieldInfo;
import org.jackie.jclassfile.model.MethodInfo;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.constantpool.impl.ClassRef;
import org.jackie.jclassfile.util.ClassNameHelper;
import org.jackie.utils.ClassName;

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
			buildAttribute(a);
		}
	}

	void buildField(FieldInfo f) {
	}

	void buildMethod(MethodInfo m) {
	}

	void buildAttribute(AttributeInfo a) {
	}
}
