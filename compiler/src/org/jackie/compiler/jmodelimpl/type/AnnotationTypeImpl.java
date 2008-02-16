package org.jackie.compiler.jmodelimpl.type;

import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.jmodelimpl.annotations.AnnotationAttributeImpl;
import org.jackie.compiler.jmodelimpl.annotations.AnnotationAttributeValueImpl;
import org.jackie.compiler.jmodelimpl.structure.JMethodImpl;
import static org.jackie.compiler.util.Helper.iterable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;

/**
 * @author Patrik Beno
 */
public class AnnotationTypeImpl implements SpecialTypeImpl {

	public JClassImpl jclass;

	public List<AnnotationAttributeImpl> attributes;

	public AnnotationTypeImpl(JClassImpl jclass) {
		this.jclass = jclass;
	}

	public AnnotationAttributeImpl getAttributeImpl(String name) {
		if (attributes == null) {
			populateAttributes();
		}
		for (AnnotationAttributeImpl a : iterable(attributes)) {
			if (a.name.equals(name)) {
				return a;
			}
		}
		throw new NoSuchElementException(String.format("%s.%s", jclass.getFQName(), name));
	}

	void populateAttributes() {
		List<AnnotationAttributeImpl> attrs = new ArrayList<AnnotationAttributeImpl>();
		for (JMethodImpl m : iterable(jclass.methods)) {

			// todo later on, rewrite this to use ClassTypeImpl to avoid filters like this
			if (m.name.equals("<init>")) { continue; }
			if (m.name.equals("<clinit>")) { continue; }
			if (!m.parameters.isEmpty()) { continue; }

			AnnotationAttributeImpl attr = new AnnotationAttributeImpl();
			attr.annotype = jclass;
			attr.name = m.name;
			attr.type = m.type;
			attr.jmethod = m;
			attr.defaultValue = new AnnotationAttributeValueImpl(null, attr, m.asmnode.annotationDefault);

			attrs.add(attr);
		}

		if (!attrs.isEmpty()) {
			attributes = attrs;
		}
	}
}
