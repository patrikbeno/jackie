package org.jackie.compiler.jmodelimpl.structure;

import org.jackie.compiler.jmodelimpl.FlagsImpl;
import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.jmodelimpl.annotations.AnnotatedImpl;
import org.jackie.jmodel.AccessMode;

/**
 * @author Patrik Beno
 */
public class JFieldImpl {

	public JClassImpl owner;

	public String name;

	public JClassImpl type;

	public AnnotatedImpl annotations;

	public AccessMode accessMode;

	public FlagsImpl flags;

	{
		annotations = new AnnotatedImpl();
	}

	public String toString() {
		return String.format("%s : %s", name, type.getFQName());
	}
}
