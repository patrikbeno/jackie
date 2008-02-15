package org.jackie.compiler.jmodelimpl.type;

import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.jmodelimpl.annotations.AnnotationAttributeImpl;

import java.util.List;

/**
 * @author Patrik Beno
 */
public class AnnotationTypeImpl implements SpecialTypeImpl {

	public JClassImpl jclass;

	public List<AnnotationAttributeImpl> attributes;

	public AnnotationAttributeImpl getAttributeImpl(String name) {
		for (AnnotationAttributeImpl a : attributes) {
			if (a.name.equals(name)) {
				return a;
			}
		}
		return null;
	}

}
