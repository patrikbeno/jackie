package org.jackie.compiler.jmodelimpl.structure;

import org.jackie.compiler.jmodelimpl.JClassImpl;

/**
 * @author Patrik Beno
 */
public class JParameterImpl {

	public String name;
	public JClassImpl jclass;

	public JParameterImpl(String name, JClassImpl jclass) {
		this.name = name;
		this.jclass = jclass;
	}
}
