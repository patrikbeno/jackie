package org.jackie.java5.annotation.impl;

import org.jackie.compiler.typeregistry.TypeRegistry;
import org.jackie.compiler.spi.Compilable;
import static org.jackie.context.ContextManager.context;
import org.jackie.context.ContextObject;
import org.jackie.java5.annotation.Annotated;
import org.jackie.java5.annotation.AnnotationType;
import org.jackie.java5.annotation.Annotations;
import org.jackie.java5.annotation.JAnnotation;
import org.jackie.java5.annotation.JAnnotationAttribute;
import org.jackie.java5.annotation.JAnnotationAttributeValue;
import org.jackie.java5.enumtype.EnumType;
import org.jackie.jvm.JClass;
import org.jackie.jvm.JNode;
import org.jackie.jvm.extension.builtin.ArrayType;
import org.jackie.jvm.extension.builtin.JPrimitive;
import org.jackie.jvm.extension.builtin.PrimitiveType;
import org.jackie.utils.Assert;
import org.jackie.utils.ClassName;
import org.jackie.utils.CollectionsHelper;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;

import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.emptyList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class AnnotationImpl implements JAnnotation, Compilable {

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
		ClassName clsname = new ClassName(Type.getType(anode.desc).getClassName());
		JClass jclass = context(TypeRegistry.class).getJClass(clsname);
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

	public JNode owner() {
		throw Assert.notYetImplemented(); // todo implement this
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

		if (jclass.extensions().supports(PrimitiveType.class)) {
			assert JPrimitive.isObjectWrapper(object.getClass());
			return object;

		} else if (jclass.equals(context(TypeRegistry.class).getJClass(String.class))) {
			assert object instanceof String;
			return object;

		} else if (jclass.equals(context(TypeRegistry.class).getJClass(Class.class))) {
			assert object instanceof Type;
			return context(TypeRegistry.class).getJClass(new ClassName(((Type) object).getClassName()));

		} else if (jclass.extensions().supports(AnnotationType.class)) {
			assert object instanceof AnnotationNode;
			return new AnnotationImpl((AnnotationNode) object, this);

		} else if (jclass.extensions().supports(EnumType.class)) {
			assert object instanceof String[] && Array.getLength(object)==2;
			String[] names = (String[]) object;
			ClassName clsname = new ClassName(Type.getObjectType(names[0]).getClassName());
			return new EnumProxy(clsname.getFQName(), names[1]);

		} else if (jclass.extensions().supports(ArrayType.class)) {
			assert object instanceof List;
			ArrayType array = jclass.extensions().get(ArrayType.class);
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
		for (JAnnotationAttributeValue a : CollectionsHelper.iterable(attributes)) {
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
			ClassLoader cl = Thread.currentThread().getContextClassLoader();//fixme context().annotationClassLoader();
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

	public void compile() {
	}
}