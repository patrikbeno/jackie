package org.jackie.compiler.jmodelimpl.annotations;

import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.jmodelimpl.structure.JMethodImpl;

/**
 * @author Patrik Beno
 */
public class AnnotationAttributeImpl {

	public JClassImpl annotype;
	public JMethodImpl jmethod;

	public String name;
	public JClassImpl type;

	public AnnotationAttributeValueImpl defaultValue;
}
