package org.jackie.compiler.jmodelimpl.type;

import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.jmodelimpl.annotations.AnnotationAttributeImpl;
import org.jackie.jmodel.type.AnnotationType;
import org.jackie.jmodel.structure.JAnnotationAttribute;
import org.jackie.jmodel.JClass;
import org.jackie.utils.Assert;

import java.util.List;
import java.util.Set;

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
