package org.jackie.compiler.bytecode;

import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.jmodelimpl.structure.JFieldImpl;
import static org.jackie.compiler.util.Context.typeRegistry;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.AnnotationNode;

/**
 * @author Patrik Beno
 */
public class JClassReader extends ByteCodeLoader implements ClassVisitor {

	JClassImpl jclass;

	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		jclass = getJClassByBName(name);
//		jclass.kind = toKind(access); // todo interface? enum? annotation?

		if (superName != null) { // all classes except java.lang.Object have superclass defined
			jclass.superclass = getJClassByBName(superName);
		}

		for (String iface : interfaces) {
			jclass.interfaces.add(typeRegistry().getJClass(getClassName(iface)));
		}
	}

	public void visitSource(String source, String debug) {
		// todo implement this
	}

	public void visitOuterClass(String owner, String name, String desc) {
		// todo implement this
	}

	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		AnnotationNode anno = new AnnotationNode(desc);
		jclass.annotations.addAsmNode(anno);
		return anno;
	}

	public void visitAttribute(Attribute attr) {
		// todo implement this
	}

	public void visitInnerClass(String name, String outerName, String innerName, int access) {
		// todo implement this
	}

	public FieldVisitor visitField(final int access, final String name, final String desc, final String signature, final Object value) {
		return new FieldVisitor() {

			JFieldImpl field;

			{
				field = new JFieldImpl();
				field.name = name;
				field.type = getJClassByDesc(desc);
				field.owner = jclass.addField(field);
				// todo value: register field initializer
			}

			public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
				AnnotationNode anno = new AnnotationNode(desc);
				field.annotations.addAsmNode(anno);
				return anno;
			}

			public void visitAttribute(Attribute attr) {
				// todo implement this
			}

			public void visitEnd() {
			}
		};
	}

	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		JMethodReader mreader = new JMethodReader(jclass, access, name, desc, signature, exceptions);
		return mreader.getMethodVisitor();
	}


	public void visitEnd() {
	}

}
