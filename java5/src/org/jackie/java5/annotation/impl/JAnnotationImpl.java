package org.jackie.java5.annotation.impl;

import org.jackie.compiler.spi.Compilable;
import org.jackie.compiler.typeregistry.TypeRegistry;
import static org.jackie.context.ContextManager.context;
import org.jackie.java5.annotation.Annotated;
import org.jackie.java5.annotation.AnnotationType;
import org.jackie.java5.annotation.JAnnotation;
import org.jackie.java5.annotation.JAnnotationElementValue;
import org.jackie.java5.annotation.JAnnotations;
import org.jackie.jclassfile.attribute.anno.ElementValue;
import org.jackie.jclassfile.attribute.anno.Annotation;
import org.jackie.jclassfile.attribute.anno.ElementValue.AnnoElementValue;
import org.jackie.jclassfile.attribute.anno.ElementValue.ArrayElementValue;
import org.jackie.jclassfile.attribute.anno.ElementValue.ClassElementValue;
import org.jackie.jclassfile.attribute.anno.ElementValue.EnumElementValue;
import org.jackie.jclassfile.attribute.anno.ElementValue.ConstElementValue;
import org.jackie.jclassfile.util.ClassNameHelper;
import static org.jackie.jclassfile.util.ClassNameHelper.toJavaClassName;
import org.jackie.jclassfile.util.TypeDescriptor;
import org.jackie.jvm.JClass;
import org.jackie.jvm.JNode;
import org.jackie.jvm.extension.builtin.ArrayType;
import org.jackie.jvm.extension.builtin.ArrayTypeHelper;
import org.jackie.jvm.spi.AbstractJNode;
import org.jackie.jvm.spi.JModelHelper;
import org.jackie.utils.Assert;
import static org.jackie.utils.Assert.typecast;
import org.jackie.utils.ClassName;
import org.jackie.utils.CollectionsHelper;

import java.lang.ref.WeakReference;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.emptyList;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class JAnnotationImpl extends AbstractJNode implements JAnnotation, Compilable {

	AnnotationType type;

	List<JAnnotationElementValue> elements;

	WeakReference<java.lang.annotation.Annotation> proxy;


	public JAnnotationImpl(JNode owner, Annotation anno) {
		super(owner);

		JClass jclass = getJClass(anno.type());
		AnnotationType type = jclass.extensions().get(AnnotationType.class);

		for (ElementValue evalue : anno.elements()) {
			JAnnotationElementValue value = new JAnnotationElementValueImpl(
					this,
					type.getElement(evalue.name()),
					evalue
			);
			edit().addAttributeValue(value);
		}
	}


	////

	public JClass getType() {
		return type.node();
	}

	public Annotated getAnnotatedElement() {
		return owner instanceof Annotated ? (Annotated) owner : null;
	}

	public AnnotationType getJAnnotationType() {
		return type;
	}

	public List<JAnnotationElementValue> getElementValues() {
		if (elements == null) {
			return emptyList();
		}
		return Collections.unmodifiableList(elements);
	}

	public JAnnotationElementValue getElementValue(String name) {
		for (JAnnotationElementValue a : CollectionsHelper.iterable(elements)) {
			if (a.getJAnnotationElement().getName().equals(name)) {
				return a;
			}
		}
		return null;
	}

	public boolean isEditable() {
		return JModelHelper.isEditable(this);
	}

	public Editor edit() {
		return new Editor() {
			public Editor addAttributeValue(JAnnotationElementValue value) {
				if (elements == null) {
					elements = new ArrayList<JAnnotationElementValue>();
				}
				elements.add(value);
				return this;
			}

			public JAnnotation editable() {
				return JAnnotationImpl.this;
			}
		};
	}

	public void compile() {
		throw Assert.notYetImplemented(); // todo implement compile()
	}

	JClass getJClass(TypeDescriptor desc) {
		String bname = ClassNameHelper.toJavaClassName(desc.getTypeName());
		ClassName clsname = new ClassName(bname, desc.getDimensions());
		JClass jclass = context(TypeRegistry.class).getJClass(clsname);
		return jclass;
	}

	Object convert(JClass jclass, ElementValue evalue) {

		switch (evalue.tag()) {
			case BYTE:
			case CHAR:
			case DOUBLE:
			case FLOAT:
			case INT:
			case LONG:
			case SHORT:
			case BOOLEAN:
			case STRING:
				return typecast(evalue, ConstElementValue.class).value();
			
			case ENUM:
				EnumElementValue enumvalue = typecast(evalue, EnumElementValue.class);
				return new EnumProxy(toJavaClassName(enumvalue.type()), enumvalue.value());

			case CLASS:
				ClassElementValue classvalue = typecast(evalue, ClassElementValue.class);
				return new ClassProxy(toJavaClassName(classvalue.type()));

			case ANNOTATION:
				AnnoElementValue annovalue = typecast(evalue, AnnoElementValue.class);
				return new JAnnotationImpl(this, annovalue.annotation());

			case ARRAY:
				ArrayElementValue arrayvalue = typecast(evalue, ArrayElementValue.class);
				return convertArray(ArrayTypeHelper.getComponentType(jclass), arrayvalue);

			default:
				throw Assert.invariantFailed(evalue.tag());
		}

	}

	List convertArray(JClass jclass, ArrayElementValue arrayvalue) {
		List<Object> converted = new ArrayList<Object>(arrayvalue.values().size());
		for (ElementValue evalue : arrayvalue.values()) {
			converted.add(convert(jclass, evalue));
		}
		return converted;
	}

	public java.lang.annotation.Annotation proxy() {
		java.lang.annotation.Annotation a = proxy != null ? proxy.get() : null;
		if (a != null) {
			return a;
		}

		try {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();//fixme context().annotationClassLoader();
			Class cls = Class.forName(type.node().getFQName(), false, cl);

			a = (java.lang.annotation.Annotation) Proxy.newProxyInstance(
					cl,
					new Class<?>[] { cls },
					new AnnotationProxy(this));

			proxy = new WeakReference<java.lang.annotation.Annotation>(a);
			return a;

		} catch (ClassNotFoundException e) {
			throw Assert.notYetHandled(e);
		}

	}
}