package org.jackie.java5.annotation.impl;

import static org.jackie.context.ContextManager.context;
import org.jackie.java5.AbstractExtension;
import org.jackie.java5.annotation.AnnotationType;
import org.jackie.java5.annotation.JAnnotationAttribute;
import org.jackie.jvm.JClass;
import org.jackie.jvm.attribute.JAttribute;
import org.jackie.jvm.structure.JMethod;
import org.jackie.utils.Assert;
import org.jackie.utils.CollectionsHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

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

		for (JAnnotationAttribute a : CollectionsHelper.iterable(attributes)) {
			if (a.getName().equals(name)) {
				return a;
			}
		}
		throw new NoSuchElementException(String.format("%s.%s", node.getFQName(), name));
	}

	public List<JAnnotationAttribute> getAttributes() {
		return Collections.unmodifiableList(attributes);
	}

	public boolean isEditable() {
		return node().isEditable();
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

			// register
			JAnnotationAttribute attr = new JAnnotationAttributeImpl(m);
			attrs.add(attr);

			// setup default
			JAttribute adflt =
					m.attributes().getAttribute("AnnotationDefault");
//			attr.edit().setDefaultValue(new JAnnotationAttributeValueImpl(null, attr, dflt));

// fixme populate annotation default
//			JAttribute adflt =
//					m.attributes().getAttribute("AnnotationDefault");
//			Object dflt = adflt != null ? convertAsmValue(m, adflt.getValue()) : null;
//
//			attr.edit().setDefaultValue(new JAnnotationAttributeValueImpl(null, attr, dflt));
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
//			JAnnotationAttribute attr = new JAnnotationAttributeImpl(m, null);
//			attrs.add(attr);
//		}
//
//		if (!attrs.isEmpty()) {
//			attributes = attrs;
//		}
//	}
//
//	protected Object convertAsmValue(JMethod jmethod, Object asmvalue) {
//
//		if (asmvalue == null) {
//			return null;
//		}
//
//		Object converted;
//
//		// fixme JModelUtils obsoleted
//		/*if (JModelUtils.isEnum(jmethod.getType())) {
//			// enums are passed as String[2] { classname, constantname }
//			// save only constant name, enum type is remembered in annotation attribute
//			converted = ((String[]) asmvalue)[1];
//
//		} else*/ if (asmvalue instanceof Type) {
//			Type asmtype = (Type) asmvalue;
//			converted = context(TypeRegistry.class).getJClass(new ClassName(asmtype.getClassName()));
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
//			JAnnotation anno = new JAnnotationImpl(
//					(AnnotationNode) asmvalue,
//					jmethod.extensions().get(JAnnotations.class));
//			converted = anno;
//
//		} else {
//			converted = asmvalue;
//		}
//
//		return converted;
//	}

//	void populate(JAnnotationAttributeValueImpl attrvalue, Object asmvalue) {
//		if (asmvalue.getClass().isArray()) {
//			for (int i = 0; i < Array.getLength(asmvalue); i++) {
//				attrvalue.addValue(Array.get(asmvalue, i));
//			}
//
//		} else if (asmvalue instanceof Type) {
//			Type asmtype = (Type) asmvalue;
//			context(TypeRegistry.class).getJClass(new ClassName(asmtype));
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
