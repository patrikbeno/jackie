package org.jackie.compiler.bytecode;

import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.jmodelimpl.LoadLevel;
import org.jackie.compiler.jmodelimpl.structure.JFieldImpl;
import org.jackie.compiler.util.ClassName;
import static org.jackie.compiler.util.Context.context;
import static org.jackie.compiler.util.Helper.iterable;

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

	LoadLevel loadLevel;
	JClassImpl jclass;

	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		ClassName clsname = getClassName(name);

		jclass = getJClass(clsname);
		jclass.accessMode = toAccessMode(access);
		jclass.flags = toFlags(access);

		// all classes except java.lang.Object have superclass defined
		assert superName != null || jclass.getFQName().equals(Object.class.getName());
		jclass.superclass = (superName != null) ? getJClassByBName(superName) : null;

		for (String iface : iterable(interfaces)) {
			jclass.interfaces.add(context().typeRegistry().getJClass(getClassName(iface)));
		}
	}

	public void visitSource(String source, String debug) {
		jclass.source = source;
		jclass.debug = debug;
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
				field.accessMode = toAccessMode(access);
				field.flags = toFlags(access);
				
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
