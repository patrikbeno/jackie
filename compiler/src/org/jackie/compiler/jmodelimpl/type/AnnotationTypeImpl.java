package org.jackie.compiler.jmodelimpl.type;

import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.jmodelimpl.annotations.AnnotatedImpl;
import org.jackie.compiler.jmodelimpl.annotations.AnnotationAttributeImpl;
import org.jackie.compiler.jmodelimpl.annotations.AnnotationAttributeValueImpl;
import org.jackie.compiler.jmodelimpl.annotations.AnnotationImpl;
import org.jackie.compiler.jmodelimpl.structure.JMethodImpl;
import org.jackie.compiler.util.ClassName;
import static org.jackie.compiler.util.Context.context;
import static org.jackie.compiler.util.Helper.*;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
			attr.defaultValue = new AnnotationAttributeValueImpl(
					null, attr, convertAsmValue(m.type, m.asmnode.annotationDefault));

			attrs.add(attr);
		}

		if (!attrs.isEmpty()) {
			attributes = attrs;
		}
	}

	protected Object convertAsmValue(JClassImpl type, Object asmvalue) {

		if (asmvalue == null) {
			return null;
		}

		Object converted;

		if (isEnum(type)) {
			// enums are passed as String[2] { classname, constantname }
			// save only constant name, enum type is remembered in annotation attribute
			converted = ((String[]) asmvalue)[1];

		} else if (asmvalue instanceof Type) {
			Type asmtype = (Type) asmvalue;
			converted = context().typeRegistry().getJClass(new ClassName(asmtype));

		} else
		if (asmvalue.getClass().isArray() && asmvalue.getClass().getComponentType().isPrimitive()) {
			List list = new ArrayList(Array.getLength(asmvalue));
			for (int i = 0; i < Array.getLength(asmvalue); i++) {
				list.add(Array.get(asmvalue, i));
			}
			converted = list;

		} else if (asmvalue instanceof AnnotationNode) {
			AnnotatedImpl annotated = new AnnotatedImpl();
			AnnotationImpl anno = annotated.toAnnotation((AnnotationNode) asmvalue);
			converted = anno;

		} else {
			converted = asmvalue;
		}

		return converted;
	}

	void populate(AnnotationAttributeValueImpl attrvalue, Object asmvalue) {
		if (asmvalue.getClass().isArray()) {
			for (int i = 0; i < Array.getLength(asmvalue); i++) {
				attrvalue.addValue(Array.get(asmvalue, i));
			}

		} else if (asmvalue instanceof Type) {
			Type asmtype = (Type) asmvalue;
			context().typeRegistry().getJClass(new ClassName(asmtype));

		} else {
			attrvalue.setValue(asmvalue);
		}
	}

}
