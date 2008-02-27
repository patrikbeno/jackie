package org.jackie.compiler.jmodelimpl.structure;

import org.jackie.compiler.jmodelimpl.FlagsImpl;
import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.jmodelimpl.annotations.AnnotatedImpl;
import org.jackie.compiler.jmodelimpl.annotations.AnnotationImpl;
import org.jackie.jmodel.AccessMode;
import org.jackie.utils.Assert;

import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.AnnotationVisitor;

/**
 * @author Patrik Beno
 */
public class JFieldImpl {

	public JClassImpl owner;

	public String name;

	public JClassImpl type;

	public AnnotatedImpl annotations;

	public AccessMode accessMode;

	public FlagsImpl flags;

	{
		annotations = new AnnotatedImpl();
	}

	public String toString() {
		return String.format("%s : %s", name, type.getFQName());
	}

	public String bcSignature() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public void compile(FieldVisitor fv) {
		for (AnnotationImpl a : annotations) {
			AnnotationVisitor av = fv.visitAnnotation(a.type.jclass.bcDesc(), true);
			a.compile(av);
		}
	}
}
