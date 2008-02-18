package org.jackie.compiler.jmodelimpl.structure;

import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.jmodelimpl.FlagsImpl;
import org.jackie.jmodel.AccessMode;

import org.objectweb.asm.tree.MethodNode;

import java.util.List;

/**
 * @author Patrik Beno
 */
public class JMethodImpl {

	public JClassImpl owner;

	public String name;
	public JClassImpl type;
	public List<JParameterImpl> parameters;
	public List<JClassImpl> exceptions;

	public AccessMode accessMode;
	public FlagsImpl flags;

	public MethodNode asmnode;

	public String toString() {
		return String.format("%s() : %s", name, type);
	}
}
