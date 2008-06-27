package org.jackie.compiler_impl.bytecode;

import org.jackie.compiler.event.ClassListener;
import org.jackie.compiler.event.FieldListener;
import org.jackie.compiler.typeregistry.TypeRegistry;
import org.jackie.compiler_impl.jmodelimpl.LoadLevel;
import org.jackie.compiler_impl.jmodelimpl.attribute.JAttributeImpl;
import org.jackie.compiler_impl.jmodelimpl.structure.JFieldImpl;
import static org.jackie.compiler_impl.util.Helper.impl;
import static org.jackie.context.ContextManager.context;
import static org.jackie.event.Events.events;
import org.jackie.jvm.JClass;
import org.jackie.jvm.attribute.special.KindAttribute;
import org.jackie.jvm.structure.JField;
import org.jackie.utils.ClassName;
import org.jackie.utils.CollectionsHelper;
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
				.addAttribute(new KindAttribute(toKind(access)));

		// all classes except java.lang.Object have superclass defined
		assert superName != null || jclass.getFQName().equals(Object.class.getName());

		jclass.edit()
				.setAccessMode(toAccessMode(access))
				.setFlags(toFlags(access))
				.setSuperClass((superName != null) ? getJClassByBName(superName) : null);

		for (String iname : CollectionsHelper.iterable(interfaces)) {
			JClass iface = context(TypeRegistry.class).getJClass(getClassName(iname));
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
				new JAttributeImpl<String[]>(BuiltinAttribute.EnclosingMethod.name(), new String[] {owner,name,desc}));
	}

	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		if (atLeast(LoadLevel.ATTRIBUTES)) {
			return null;
		}
		AnnotationNode anno = new AnnotationNode(desc);
		jclass.attributes().edit().addAttribute(
				new JAttributeImpl<AnnotationNode>(BuiltinAttribute.RuntimeVisibleAnnotations.name(), anno));
		return anno;
	}

	public void visitAttribute(Attribute attr) {
		jclass.attributes().edit().addAttribute(new JAttributeImpl<Attribute>(attr.type, attr));
	}

	public void visitInnerClass(String name, String outerName, String innerName, int access) {
		if (atLeast(LoadLevel.CLASS)) {
			return;
		}
		jclass.attributes().edit().addAttribute(
				new JAttributeImpl<Object[]>(BuiltinAttribute.InnerClasses.name(), new Object[]{name,outerName,innerName,access}));
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

				jfield.attributes().edit().addAttribute(
						new JAttributeImpl<Object>(BuiltinAttribute.ConstantValue.name(), value));
			}

			public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
				AnnotationNode anno = new AnnotationNode(desc);
				jfield.attributes().edit().addAttribute(
						new JAttributeImpl<AnnotationNode>(BuiltinAttribute.RuntimeVisibleAnnotations.name(), anno));
				return anno;
			}

			public void visitAttribute(Attribute attr) {
				jclass.attributes().edit().addAttribute(new JAttributeImpl<Attribute>(attr.type, attr));
			}

			public void visitEnd() {
				events(FieldListener.class).loaded(jfield);
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
		events(ClassListener.class).loaded(jclass);
	}

	protected boolean atLeast(LoadLevel level) {
		return impl(jclass).loadLevel.atLeast(level);
	}

}
