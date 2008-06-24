package org.jackie.compiler_impl.bytecode;

import org.jackie.compiler_impl.jmodelimpl.LoadLevel;
import org.jackie.compiler_impl.jmodelimpl.attribute.JAttributeImpl;
import org.jackie.compiler_impl.jmodelimpl.structure.JMethodImpl;
import org.jackie.compiler_impl.jmodelimpl.structure.JParameterImpl;
import org.jackie.compiler_impl.util.Helper;
import org.jackie.jvm.JClass;
import org.jackie.jvm.structure.JMethod;
import org.jackie.jvm.structure.JParameter;
import static org.jackie.event.Events.events;
import org.jackie.compiler.event.MethodListener;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import static org.objectweb.asm.Type.getReturnType;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class JMethodReader extends ByteCodeLoader {

	JClass jclass;
	LoadLevel level;
	JMethod jmethod;

	// saved for ASM MethodNode
	int access;
	String name, desc, signature;
	String[] exceptions;

	public JMethodReader(JClass jclass, LoadLevel level,
								int access, String name, String desc, String signature,
								String[] exceptions) {
		this.jclass = jclass;
		this.level = level;

		this.name = name;
		this.desc = desc;
		this.signature = signature;
		this.exceptions = exceptions;

		jmethod = new JMethodImpl(jclass);
		jmethod.edit()
				.setName(name)
				.setType(getJClassByType(getReturnType(desc)))
				.setParameters(populateArgs(desc))
				.setExceptions(populateExceptions(exceptions))
				.setAccessMode(toAccessMode(access))
				.setFlags(toFlags(access));

		jclass.edit().addMethod(jmethod);
	}

	List<JParameter> populateArgs(String desc) {
		Type[] argtypes = Type.getArgumentTypes(desc);
		if (Helper.empty(argtypes)) { return Collections.emptyList(); }

		List<JParameter> args = new ArrayList<JParameter>(argtypes.length);
		for (int i=0; i<argtypes.length; i++) {
			Type t = argtypes[i];
			JClass c = getJClassByType(t);
			JParameter p = new JParameterImpl();
			p.edit()
					.setName("p" + i)
					.setType(c);

			args.add(p);
		}

		return args;
	}

	List<JClass> populateExceptions(String[] exceptions) {
		if (Helper.empty(exceptions)) { return Collections.emptyList(); }

		List<JClass> jexceptions = new ArrayList<JClass>(exceptions.length);
		for (String bname : exceptions) {
			JClass c = getJClassByBName(bname);
			assert c != null;
			jexceptions.add(c);
		}

		return jexceptions;
	}

	MethodVisitor getMethodVisitor() {
		return new MethodNode(access, name, desc, signature, exceptions) {

			public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
				AnnotationNode anno = new AnnotationNode(desc);
				jmethod.attributes().edit().addAttribute(
						new JAttributeImpl<AnnotationNode>("RuntimeVisibleAnnotations", anno));
				return anno;

			}

			public void visitAttribute(Attribute attr) {
				jclass.attributes().edit().addAttribute(new JAttributeImpl<Attribute>(attr.type, attr));
			}

			public void visitEnd() {
				super.visitEnd();
				if (annotationDefault != null) {
					jmethod.attributes().edit().addAttribute(
							new JAttributeImpl<Object>("AnnotationDefault", annotationDefault));
				}
				events(MethodListener.class).loaded(jmethod);
			}
		};
	}

}
