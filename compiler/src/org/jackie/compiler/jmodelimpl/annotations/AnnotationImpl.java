package org.jackie.compiler.jmodelimpl.annotations;

import static org.jackie.compiler.util.Context.context;
import org.jackie.jmodel.extension.annotation.AnnotationType;
import org.jackie.jmodel.extension.annotation.Annotated;
import org.jackie.jmodel.extension.annotation.JAnnotation;
import org.jackie.jmodel.extension.annotation.JAnnotationAttributeValue;
import org.jackie.utils.Assert;

import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

	public AnnotationImpl(AnnotationType type, Object owner) {
		assert type != null;
		assert Annotated.class.isInstance(owner) || JAnnotation.class.isInstance(owner);
		this.owner = owner;
		this.type = type;
	}

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
		for (JAnnotationAttributeValue a : attributes) {
			if (a.getAnnotationAttribute().getName().equals(name)) {
				return a;
			}
		}
		return null;
	}

	public List<JAnnotationAttributeValue> getAttributes() {
		return Collections.unmodifiableList(attributes);
	}

	public Editor edit() {
		return new Editor() {
			public Editor addAttributeValue(JAnnotationAttributeValue value) {
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

	public void addAttributeValue(JAnnotationAttributeValue value) {
		assert value != null;
		if (attributes == null) {
			attributes = new ArrayList<JAnnotationAttributeValue>();
		}
		attributes.add(value);
	}

}
