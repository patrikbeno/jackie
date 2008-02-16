package org.jackie.compiler.jmodelimpl.structure;

import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.jmodelimpl.annotations.AnnotatedImpl;

/**
 * @author Patrik Beno
 */
public class JFieldImpl {

	public JClassImpl owner;

	public String name;

	public JClassImpl type;

	public AnnotatedImpl annotations;

	{
		annotations = new AnnotatedImpl();
	}

	public String toString() {
		return String.format("%s : %s", name, type.getFQName());
	}
}
