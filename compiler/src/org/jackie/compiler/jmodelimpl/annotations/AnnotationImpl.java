package org.jackie.compiler.jmodelimpl.annotations;

import static org.jackie.compiler.util.Context.context;
import static org.jackie.compiler.util.Helper.iterable;
import org.jackie.compiler.util.ClassName;
import org.jackie.compiler.util.EnumProxy;
import org.jackie.jmodel.extension.annotation.AnnotationType;
import org.jackie.jmodel.extension.annotation.Annotated;
import org.jackie.jmodel.extension.annotation.JAnnotation;
import org.jackie.jmodel.extension.annotation.JAnnotationAttributeValue;
import org.jackie.jmodel.extension.annotation.Annotations;
import org.jackie.jmodel.extension.annotation.JAnnotationAttribute;
import org.jackie.jmodel.extension.Extensible;
import org.jackie.jmodel.extension.builtin.JPrimitive;
import org.jackie.jmodel.extension.builtin.ArrayType;
import org.jackie.jmodel.JClass;
import org.jackie.jmodel.util.JModelUtils;
import org.jackie.utils.Assert;
import static org.jackie.utils.Assert.typecast;
import org.objectweb.asm.tree.AnnotationNode;
import static org.objectweb.asm.Type.getType;
import org.objectweb.asm.Type;

import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.Proxy;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Iterator;
import static java.util.Collections.emptyList;

/**
 * @author Patrik Beno
 */
public class AnnotationImpl implements JAnnotation {

	/**
	 * Owner of this annotation: can be {@link Annotated an annotated element}
	 * or {@link JAnnotation enclosing annotation}
	 */
	Object owner;

	/**
	 * type of this annotation
	 */
	AnnotationType type;

	/**
	 * Values of the annotation attributes
	 */
	List<JAnnotationAttributeValue> attributes;

	WeakReference<Annotation> proxy;

	public AnnotationImpl(AnnotationType type, Annotations owner) {
		init(type, owner);
	}

	public AnnotationImpl(AnnotationType type, JAnnotation owner) {
		init(type, owner);
	}

	public AnnotationImpl(AnnotationNode anode, Annotations owner) {
		this(anode, (Object) owner);
	}

	public AnnotationImpl(AnnotationNode anode, JAnnotation owner) {
		this(anode, (Object) owner);
	}

	private AnnotationImpl(AnnotationNode anode, Object owner) {
		ClassName clsname = new ClassName(getType(anode.desc));
		JClass jclass = context().typeRegistry().getJClass(clsname);
		AnnotationType type = jclass.extensions().get(AnnotationType.class);

		init(type, owner);

		List asmvalues = (anode.values != null) ? anode.values : Collections.emptyList();
		Iterator it = asmvalues.iterator();
		while (it.hasNext()) {
			String name = (String) it.next();
			assert it.hasNext();
			Object object = it.next();

			JAnnotationAttributeValue value = createAttributeValue(this, name, object);
			edit().addAttributeValue(value);
		}
	}

	void init(AnnotationType type, Object owner) {
		assert type != null;
		assert owner != null;
//		assert owner instanceof Extensible && ((Extensible)owner).extensions().supports(Annotations.class) || owner instanceof JAnnotation;
		assert owner instanceof Annotations || owner instanceof JAnnotation;
		this.owner = owner;
		this.type = type;
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
			return new AnnotationImpl((AnnotationNode) object, this);

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


	////



	public Annotated getAnnotatedElement() {
		return owner instanceof Annotated ? (Annotated) owner : null;
	}

	public JAnnotation getEnclosingAnnotation() {
		return owner instanceof JAnnotation ? (JAnnotation) owner : null;
	}

	public AnnotationType getJAnnotationType() {
		return type;
	}

	public JAnnotationAttributeValue getAttribute(String name) {
		for (JAnnotationAttributeValue a : iterable(attributes)) {
			if (a.getAnnotationAttribute().getName().equals(name)) {
				return a;
			}
		}
		return null;
	}

	public List<JAnnotationAttributeValue> getAttributes() {
		if (attributes == null) {
			return emptyList();
		}
		return Collections.unmodifiableList(attributes);
	}

	public boolean isEditable() {
		throw Assert.notYetImplemented(); // todo implement this 
	}

	public Editor edit() {
		return new Editor() {
			public Editor addAttributeValue(JAnnotationAttributeValue value) {
				if (attributes == null) {
					attributes = new ArrayList<JAnnotationAttributeValue>();
				}
				attributes.add(value);
				return this;
			}

			public JAnnotation editable() {
				return AnnotationImpl.this;
			}
		};
	}

	public Annotation proxy() {
		Annotation a = proxy != null ? proxy.get() : null;
		if (a != null) {
			return a;
		}

		try {
			ClassLoader cl = context().annotationClassLoader();
			Class cls = Class.forName(type.node().getFQName(), false, cl);

			//noinspection UnusedAssignment
			a = (Annotation) Proxy.newProxyInstance(
					cl,
					new Class<?>[] { cls },
					new AnnotationProxy(this));

			proxy = new WeakReference<Annotation>(a);
			return a;

		} catch (ClassNotFoundException e) {
			throw Assert.notYetHandled(e);
		}

	}

}
