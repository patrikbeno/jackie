package org.jackie.compiler.jmodelimpl.annotations;

import org.jackie.compiler.jmodelimpl.type.AnnotationTypeImpl;
import org.jackie.compiler.util.Context;
import static org.jackie.compiler.util.Helper.iterable;
import org.jackie.jmodel.type.AnnotationType;
import org.jackie.utils.Assert;

import org.objectweb.asm.AnnotationVisitor;

import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Patrik Beno
 */
public class AnnotationImpl {

	/**
	 * Owning (wrapping) annotation (in case of nested annotations)
	 */
	public AnnotationImpl owner;

	/**
	 * Annotated element
	 */
	public AnnotatedImpl annotated;

	/**
	 * type of this annotation
	 */
	public AnnotationTypeImpl type;

	/**
	 * Values of the annotation attributes
	 */
	public List<AnnotationAttributeValueImpl> attributes;

	WeakReference<Annotation> proxy;

	public AnnotationImpl(AnnotationImpl owner, AnnotationType type) {
		this(owner, (AnnotationTypeImpl) type);
	}

	public AnnotationImpl(AnnotationImpl owner, AnnotationTypeImpl type) {
		this.owner = owner;
		this.type = type;
	}

	public AnnotationImpl(AnnotatedImpl annotated, AnnotationTypeImpl type) {
		assert annotated != null;
		assert type != null;
		this.annotated = annotated;
		this.type = type;
	}

	public AnnotationAttributeValueImpl getAttributeValue(AnnotationAttributeImpl attr) {
		for (AnnotationAttributeValueImpl a : iterable(attributes)) {
			if (a.attribute.equals(attr)) {
				return a;
			}
		}
		return null;
	}

	public Annotation proxy() {
		Annotation a = proxy != null ? proxy.get() : null;
		if (a != null) {
			return a;
		}

		try {
			ClassLoader cl = Context.context().annotationClassLoader();
			Class cls = Class.forName(type.jclass.getFQName(), false, cl);

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

	public void addAttributeValue(AnnotationAttributeValueImpl value) {
		assert value != null;
		if (attributes == null) {
			attributes = new ArrayList<AnnotationAttributeValueImpl>();
		}
		attributes.add(value);
	}

	public void compile(AnnotationVisitor av) {
		throw Assert.notYetImplemented(); // todo implement this
	}
}
