package org.jackie.compiler.bytecode;

import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.jmodelimpl.structure.JMethodImpl;
import org.jackie.compiler.jmodelimpl.structure.JParameterImpl;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import static org.objectweb.asm.Type.getReturnType;
import org.objectweb.asm.tree.MethodNode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class JMethodReader extends ByteCodeLoader {

	JClassImpl jclass;
	JMethodImpl jmethod;

	public JMethodReader(JClassImpl jclass,
								int access, String name, String desc, String signature, String[] exceptions) {
		this.jclass = jclass;

		jmethod = new JMethodImpl();
		jmethod.name = name;
		jmethod.type = getJClassByType(getReturnType(desc));
		jmethod.parameters = populateArgs(desc);
		jmethod.exceptions = populateExceptions(exceptions);
		jmethod.asmnode = new MethodNode(access, name, desc, signature, exceptions);
		
		jmethod.owner = jclass.addMethod(jmethod);
	}

	List<JParameterImpl> populateArgs(String desc) {
		Type[] argtypes = Type.getArgumentTypes(desc);
		if (empty(argtypes)) { return Collections.emptyList(); }

		List<JParameterImpl> args = new ArrayList<JParameterImpl>(argtypes.length);
		for (int i=0; i<argtypes.length; i++) {
			Type t = argtypes[i];
			JClassImpl c = getJClassByType(t);
			JParameterImpl p = new JParameterImpl("p"+i, c); // default parameter naming
			args.add(p);
		}

		return args;
	}

	List<JClassImpl> populateExceptions(String[] exceptions) {
		if (empty(exceptions)) { return Collections.emptyList(); }

		List<JClassImpl> jexceptions = new ArrayList<JClassImpl>(exceptions.length);
		for (String bname : exceptions) {
			JClassImpl c = getJClassByBName(bname);
			jexceptions.add(c);
		}

		return jexceptions;
	}

	MethodVisitor getMethodVisitor() {
		return jmethod.asmnode;
	}

	static boolean empty(Object array) {
		return array == null || Array.getLength(array)==0;
	}

	static int length(Object array) {
		return (array != null) ?  Array.getLength(array) : 0;
	}

}
