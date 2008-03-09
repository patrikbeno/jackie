package org.jackie.compiler.jmodelimpl.type;

import org.jackie.compiler.jmodelimpl.annotations.AnnotationsImpl;
import org.jackie.compiler.jmodelimpl.annotations.AnnotationAttributeImpl;
import org.jackie.compiler.jmodelimpl.annotations.AnnotationAttributeValueImpl;
import org.jackie.compiler.util.ClassName;
import static org.jackie.compiler.util.Context.context;
import static org.jackie.compiler.util.Helper.iterable;
import org.jackie.jmodel.JClass;
import org.jackie.jmodel.util.JModelUtils;
import org.jackie.jmodel.structure.JMethod;
import org.jackie.jmodel.extension.annotation.JAnnotationAttribute;
import org.jackie.jmodel.extension.annotation.JAnnotation;
import org.jackie.jmodel.extension.annotation.AnnotationType;
import org.jackie.utils.Assert;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

/**
 * @author Patrik Beno
 */
public class AnnotationTypeImpl extends AbstractExtension<JClass> implements AnnotationType {

	protected List<JAnnotationAttribute> attributes;

	public AnnotationTypeImpl(JClass node) {
		super(node);
	}

	public Set<String> getAttributeNames() {
		populateAttributes();
		Set<String> names = new HashSet<String>();
		for (JAnnotationAttribute a : attributes) {
			names.add(a.getName());
		}
		return names;
	}

	public JAnnotationAttribute getAttribute(String name) {
		populateAttributes();

		for (JAnnotationAttribute a : iterable(attributes)) {
			if (a.getName().equals(name)) {
				return a;
			}
		}
		throw new NoSuchElementException(String.format("%s.%s", node.getFQName(), name));
	}

	public List<JAnnotationAttribute> getAttributes() {
		return Collections.unmodifiableList(attributes);
	}

	public Editor edit() {
		return new Editor() {
			public Editor addAtribute(JAnnotationAttribute attribute) {
				throw Assert.notYetImplemented(); // todo implement this
			}

			public AnnotationType editable() {
				return AnnotationTypeImpl.this;
			}
		};
	}

	void populateAttributes() {
		if (attributes != null) {
			return;
		}

		List<JAnnotationAttribute> attrs = new ArrayList<JAnnotationAttribute>();
		for (JMethod m : node().getMethods()) {

			// todo can this filtering be somewhat improved / cleaned up?
			if (m.getName().equals("<init>")) { continue; }
			if (m.getName().equals("<clinit>")) { continue; }
			if (!m.getParameters().isEmpty()) { continue; }

			JAnnotationAttribute attr = new AnnotationAttributeImpl(m, null);
			attrs.add(attr);
		}

		if (!attrs.isEmpty()) {
			attributes = attrs;
		}
	}


	//	public List<JAnnotationAttribute> attributes;
//
//	public AnnotationTypeImpl(JClass jclass) {
//		super(jclass);
//	}
//
//	public JAnnotationAttribute getAttribute(String name) {
//		if (attributes == null) {
//			populateAttributes();
//		}
//		for (JAnnotationAttribute a : iterable(attributes)) {
//			if (a.getName().equals(name)) {
//				return a;
//			}
//		}
//		throw new NoSuchElementException(String.format("%s.%s", jclass.getFQName(), name));
//	}
//
//	void populateAttributes() {
//		List<JAnnotationAttribute> attrs = new ArrayList<JAnnotationAttribute>();
//		for (JMethod m : jclass.getMethods()) {
//
//			// todo can this filtering be somewhat improved / cleaned up?
//			if (m.getName().equals("<init>")) { continue; }
//			if (m.getName().equals("<clinit>")) { continue; }
//			if (!m.getParameters().isEmpty()) { continue; }
//
//			JAnnotationAttribute attr = new AnnotationAttributeImpl(m, null);
//			attrs.add(attr);
//		}
//
//		if (!attrs.isEmpty()) {
//			attributes = attrs;
//		}
//	}
//
//	protected Object convertAsmValue(JClass type, Object asmvalue) {
//
//		if (asmvalue == null) {
//			return null;
//		}
//
//		Object converted;
//
//		if (JModelUtils.isEnum(type)) {
//			// enums are passed as String[2] { classname, constantname }
//			// save only constant name, enum type is remembered in annotation attribute
//			converted = ((String[]) asmvalue)[1];
//
//		} else if (asmvalue instanceof Type) {
//			Type asmtype = (Type) asmvalue;
//			converted = context().typeRegistry().getJClass(new ClassName(asmtype));
//
//		} else
//		if (asmvalue.getClass().isArray() && asmvalue.getClass().getComponentType().isPrimitive()) {
//			List list = new ArrayList(Array.getLength(asmvalue));
//			for (int i = 0; i < Array.getLength(asmvalue); i++) {
//				list.add(Array.get(asmvalue, i));
//			}
//			converted = list;
//
//		} else if (asmvalue instanceof AnnotationNode) {
//			AnnotationsImpl annotations = new AnnotationsImpl();
//			JAnnotation anno = annotations.toAnnotation((AnnotationNode) asmvalue);
//			converted = anno;
//
//		} else {
//			converted = asmvalue;
//		}
//
//		return converted;
//	}
//
//	void populate(AnnotationAttributeValueImpl attrvalue, Object asmvalue) {
//		if (asmvalue.getClass().isArray()) {
//			for (int i = 0; i < Array.getLength(asmvalue); i++) {
//				attrvalue.addValue(Array.get(asmvalue, i));
//			}
//
//		} else if (asmvalue instanceof Type) {
//			Type asmtype = (Type) asmvalue;
//			context().typeRegistry().getJClass(new ClassName(asmtype));
//
//		} else {
//			attrvalue.setValue(asmvalue);
//		}
//	}
//
//	public SpecialTypeEditor edit() {
//		throw Assert.notYetImplemented(); // todo implement this
//	}

}
