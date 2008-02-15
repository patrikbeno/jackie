package org.jackie.compiler.jmodelimpl.structure;

import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.objectweb.asm.tree.MethodNode;

import java.util.List;

/**
 * @author Patrik Beno
 */
public class JMethodImpl {
	public String name;
	public JClassImpl type;
	public List<JParameterImpl> parameters;
	public List<JClassImpl> exceptions;

	public MethodNode asmnode;
}
