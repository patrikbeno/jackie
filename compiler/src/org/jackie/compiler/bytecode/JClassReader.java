package org.jackie.compiler.bytecode;

import org.jackie.compiler.jmodelimpl.LoadLevel;
import org.jackie.compiler.jmodelimpl.structure.JFieldImpl;
import org.jackie.compiler.util.ClassName;
import static org.jackie.compiler.util.Context.context;
import static org.jackie.compiler.util.Helper.iterable;
import static org.jackie.compiler.util.Helper.impl;
import org.jackie.compiler.jmodelimpl.attribute.impl.EnclosingMethodAttribute;
import org.jackie.compiler.jmodelimpl.attribute.impl.InnerClassesAttribute;
import org.jackie.compiler.jmodelimpl.attribute.impl.RuntimeVisibleAnnotationsAttribute;
import org.jackie.compiler.jmodelimpl.attribute.impl.KindAttribute;
import org.jackie.utils.Assert;
import org.jackie.jmodel.JClass;
import org.jackie.jmodel.attribute.Attributes;
import org.jackie.jmodel.structure.JField;

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

	JClass jclass;
	LoadLevel level;

	public JClassReader(LoadLevel level) {
		this.level = level;
	}

	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {

		ClassName clsname = getClassName(name);
		jclass = getJClass(clsname);

		jclass.attributes().edit()
				.addAttribute(KindAttribute.class, new KindAttribute(toKind(access)));

		// all classes except java.lang.Object have superclass defined
		assert superName != null || jclass.getFQName().equals(Object.class.getName());

		jclass.edit()
				.setAccessMode(toAccessMode(access))
				.setFlags(toFlags(access))
				.setSuperClass((superName != null) ? getJClassByBName(superName) : null);

		for (String iname : iterable(interfaces)) {
			JClass iface = context().typeRegistry().getJClass(getClassName(iname));
			jclass.edit().addInterface(iface);
		}

	}

	public void visitSource(String source, String debug) {
		if (atLeast(LoadLevel.ATTRIBUTES)) {
			return;
		}

		// todo implement SourceFile attribute

//		jclass.source = source;
//		jclass.debug = debug;
	}

	public void visitOuterClass(String owner, String name, String desc) {
		if (atLeast(LoadLevel.CLASS)) {
			return;
		}
		jclass.attributes().edit().addAttribute(
				EnclosingMethodAttribute.class, 
				new EnclosingMethodAttribute(owner, name, desc)
		);
	}

	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		if (atLeast(LoadLevel.ATTRIBUTES)) {
			return null;
		}
		return loadAnnotation(jclass.attributes(), desc);
	}

	public void visitAttribute(Attribute attr) {
		// todo implement this
		Assert.logNotYetImplemented();
	}

	public void visitInnerClass(String name, String outerName, String innerName, int access) {
		if (atLeast(LoadLevel.CLASS)) {
			return;
		}
		jclass.attributes().edit().addAttribute(
				InnerClassesAttribute.class,
				new InnerClassesAttribute(name, outerName, innerName, access)
		);
	}

	public FieldVisitor visitField(final int access, final String name, final String desc, final String signature, final Object value) {
		if (atLeast(LoadLevel.API)) {
			return null;
		}

		return new FieldVisitor() {

			JField jfield;

			{
				jfield = new JFieldImpl();
				jfield.edit()
						.setName(name)
						.setType(getJClassByDesc(desc))
						.setAccessMode(toAccessMode(access))
						.setFlags(toFlags(access));

				// todo value: register field initializer
			}

			public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
				return loadAnnotation(jfield.attributes(), desc);
			}

			public void visitAttribute(Attribute attr) {
				// todo implement this
				Assert.logNotYetImplemented();
			}

			public void visitEnd() {
			}
		};
	}

	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		if (atLeast(LoadLevel.API)) {
			return null;
		}

		JMethodReader mreader = new JMethodReader(jclass, level, access, name, desc, signature, exceptions);
		return mreader.getMethodVisitor();
	}


	public void visitEnd() {
		impl(jclass).loadLevel = level;
	}

	protected boolean atLeast(LoadLevel level) {
		return impl(jclass).loadLevel.atLeast(level);
	}

	protected AnnotationVisitor loadAnnotation(Attributes attributes, String desc) {
		if (impl(jclass).loadLevel.atLeast(LoadLevel.ATTRIBUTES)) {
			return null;
		}

		RuntimeVisibleAnnotationsAttribute attr;

		attr = attributes.getAttribute(RuntimeVisibleAnnotationsAttribute.class);
		if (attr == null) {
			attributes.edit().addAttribute(
					RuntimeVisibleAnnotationsAttribute.class,
					attr = new RuntimeVisibleAnnotationsAttribute()
			);
		}

		AnnotationNode anno = new AnnotationNode(desc);
		attr.add(anno);

		return anno;

	}


}
