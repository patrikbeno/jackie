package org.jackie.compiler.jmodelimpl.attribute.impl;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.tree.AnnotationNode;
import org.jackie.jmodel.attribute.JAttribute;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Patrik Beno
 */
public class RuntimeVisibleAnnotationsAttribute extends Attribute implements JAttribute {

	static public final String NAME = "RuntimeVisibeAnnotations"; 

	List<AnnotationNode> annos;

	public RuntimeVisibleAnnotationsAttribute() {
		super(NAME);
	}

	public String getName() {
		return type;
	}

	public void add(AnnotationNode anno) {
		if (annos == null) {
			annos = new ArrayList<AnnotationNode>();
		}
		annos.add(anno);
	}
}
