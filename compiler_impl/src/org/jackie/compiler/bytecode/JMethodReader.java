package org.jackie.compiler.bytecode;

import org.jackie.compiler.LoadLevel;
import org.jackie.java5.annotation.impl.AnnotationDefaultAttribute;
import org.jackie.compiler.jmodelimpl.structure.JMethodImpl;
import org.jackie.compiler.jmodelimpl.structure.JParameterImpl;
import org.jackie.compiler.util.Helper;
import org.jackie.jmodel.JClass;
import org.jackie.jmodel.structure.JMethod;
import org.jackie.jmodel.structure.JParameter;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import static org.objectweb.asm.Type.getReturnType;
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
			jexceptions.add(c);
		}

		return jexceptions;
	}

	MethodVisitor getMethodVisitor() {
		return new MethodNode(access, name, desc, signature, exceptions) {
			public void visitEnd() {
				super.visitEnd();
				jmethod.attributes().edit().addAttribute(
						AnnotationDefaultAttribute.class,
						new AnnotationDefaultAttribute(annotationDefault));
			}
		};
	}

}
