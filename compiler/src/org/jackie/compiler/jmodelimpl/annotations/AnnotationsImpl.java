package org.jackie.compiler.jmodelimpl.annotations;

import org.jackie.compiler.util.ClassName;
import static org.jackie.compiler.util.Context.context;
import org.jackie.compiler.util.EnumProxy;
import static org.jackie.compiler.util.Helper.assertEditable;
import org.jackie.jmodel.JClass;
import org.jackie.jmodel.JNode;
import org.jackie.jmodel.extension.annotation.AnnotationType;
import org.jackie.jmodel.extension.annotation.JAnnotation;
import org.jackie.jmodel.extension.builtin.ArrayType;
import org.jackie.jmodel.extension.builtin.JPrimitive;
import org.jackie.jmodel.extension.annotation.JAnnotationAttribute;
import org.jackie.jmodel.extension.annotation.JAnnotationAttributeValue;
import org.jackie.jmodel.extension.annotation.Annotations;
import org.jackie.jmodel.util.JModelUtils;
import org.jackie.utils.Assert;
import static org.jackie.utils.Assert.typecast;
import org.objectweb.asm.Type;
import static org.objectweb.asm.Type.getType;
import org.objectweb.asm.tree.AnnotationNode;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class AnnotationsImpl implements Annotations {

	JNode node;

	List<JAnnotation> annotations;

	List<AnnotationNode> asmnodes;

	public JNode node() {
		return node;
	}

	public List<JAnnotation> getJAnnotations() {
		return Collections.unmodifiableList(annotations);
	}

	public List<? extends Annotation> getAnnotations() {
		List<Annotation> proxies = new ArrayList<Annotation>();
		for (JAnnotation anno : annotations) {
			AnnotationImpl impl = typecast(anno, AnnotationImpl.class);
			proxies.add(impl.proxy());
		}
		return proxies;
	}

	public <T extends Annotation> T getAnnotation(Class<T> type) {
		assert type != null;
		for (JAnnotation anno : annotations) {
			if (type.getName().equals(anno.getJAnnotationType().node().getFQName())) {
				AnnotationImpl impl = typecast(anno, AnnotationImpl.class);
				return typecast(impl.proxy(), type);
			}
		}
		return null;
	}


	/// Editable ///


	public Editor edit() {
		assertEditable();
		return new Editor() {
			public Editor addAnnotation(JAnnotation annotation) {
				annotations.add(annotation);
				return this;
			}

			public Annotations editable() {
				return AnnotationsImpl.this;
			}
		};
	}


	//////////////////




	public void addAsmNode(AnnotationNode anode) {
		if (asmnodes == null) {
			asmnodes = new ArrayList<AnnotationNode>();
		}
		asmnodes.add(anode);
	}

	void buildfromAsmNodes() {
		annotations = new ArrayList<JAnnotation>();
		for (AnnotationNode an : asmnodes) {
			JAnnotation a = toAnnotation(an);
			annotations.add(a);
		}
	}

	public JAnnotation toAnnotation(AnnotationNode an) {
		ClassName clsname = new ClassName(getType(an.desc));
		JClass jclass = context().typeRegistry().getJClass(clsname);
		JAnnotation anno = new AnnotationImpl(
				jclass.extensions().get(AnnotationType.class), this);

		List asmvalues = (an.values != null) ? an.values : Collections.emptyList();
		Iterator it = asmvalues.iterator();
		while (it.hasNext()) {
			String name = (String) it.next();
			assert it.hasNext();
			Object object = it.next();

			JAnnotationAttributeValue value = createAttributeValue(anno, name, object);
			anno.edit().addAttributeValue(value);
		}

		return anno;
	}

	JAnnotationAttributeValue createAttributeValue(JAnnotation anno, String name, Object object) {
		JAnnotationAttribute attrdef = anno.getJAnnotationType().getAttribute(name);
		assert attrdef != null;

		Object converted = convert(attrdef.getType(), object);
		JAnnotationAttributeValue value = new AnnotationAttributeValueImpl(anno, attrdef, converted);

		return value;
	}

	Object convert(JClass jclass, Object object) {

		if (JModelUtils.isPrimitive(jclass)) {
			assert JPrimitive.isObjectWrapper(object.getClass());
			return object;

		} else if (jclass.equals(context().typeRegistry().getJClass(String.class))) {
			assert object instanceof String;
			return object;

		} else if (jclass.equals(context().typeRegistry().getJClass(Class.class))) {
			assert object instanceof Type;
			return context().typeRegistry().getJClass(new ClassName((Type) object));

		} else if (JModelUtils.isAnnotation(jclass)) {
			assert object instanceof AnnotationNode;
			return toAnnotation((AnnotationNode) object);

		} else if (JModelUtils.isEnum(jclass)) {
			assert object instanceof String[] && Array.getLength(object)==2;
			String[] names = (String[]) object;
			ClassName clsname = new ClassName(Type.getObjectType(names[0]));
			return new EnumProxy(clsname.getFQName(), names[1]);

		} else if (JModelUtils.isArray(jclass)) {
			assert object instanceof List;
			ArrayType array = JModelUtils.asArray(jclass);
			return convertArray(array.getComponentType(), (List) object);

		} else {
			throw Assert.invariantFailed("Annotation attribute type not handled: %s", jclass);
		}
	}

	List convertArray(JClass jclass, List list) {
		List converted = new ArrayList(list.size());
		for (Object object : list) {
			//noinspection unchecked
			converted.add(convert(jclass, object));
		}
		return converted;
	}
}


