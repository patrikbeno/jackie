package org.jackie.java5.annotation.impl;

import org.jackie.compiler.spi.Compilable;
import org.jackie.compiler.spi.CompilableHelper;
import org.jackie.java5.annotation.JAnnotation;
import org.jackie.java5.annotation.JAnnotations;
import org.jackie.jvm.Editable;
import org.jackie.jvm.JClass;
import org.jackie.jvm.JNode;
import org.jackie.jvm.attribute.Attributed;
import org.jackie.jvm.attribute.Attributes;
import org.jackie.jvm.attribute.JAttribute;
import org.jackie.utils.Assert;
import static org.jackie.utils.Assert.typecast;
import static org.jackie.utils.CollectionsHelper.iterable;
import org.jackie.jclassfile.attribute.anno.RuntimeVisibleAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.lang.annotation.Annotation;

/**
 * @author Patrik Beno
 */
public class JAnnotationsImpl implements JAnnotations, Compilable {

	JNode node;

	List<JAnnotation> annotations;

	public JAnnotationsImpl(JNode node) {
		this.node = node;
	}

	public JNode node() {
		return node;
	}

	public List<JAnnotation> getJAnnotations() {
		buildAnnotations();
		return Collections.unmodifiableList(annotations);
	}

	public JAnnotation getJAnnotation(JClass jclass) {
		throw Assert.notYetImplemented(); // todo implement this
	}

	List<? extends Annotation> getAnnotations() {
		buildAnnotations();
		List<Annotation> proxies = new ArrayList<Annotation>();
		for (JAnnotation anno : annotations) {
			JAnnotationImpl impl = typecast(anno, JAnnotationImpl.class);
			proxies.add(impl.proxy());
		}
		return proxies;
	}

	public <T extends Annotation> T getAnnotation(Class<T> type) {
		assert type != null;
		buildAnnotations();

		for (JAnnotation anno : iterable(annotations)) {
			if (type.getName().equals(anno.getJAnnotationType().node().getFQName())) {
				JAnnotationImpl impl = typecast(anno, JAnnotationImpl.class);
				return typecast(impl.proxy(), type);
			}
		}
		return null;
	}


	/// Editable ///


	public boolean isEditable() {
		return (node() instanceof Editable) && ((Editable) node()).isEditable();
	}

	public Editor edit() {
		return new Editor() {
			public Editor addAnnotation(JAnnotation annotation) {
				if (annotations == null) {
					annotations = new LinkedList<JAnnotation>();
				}
				annotations.add(annotation);
				return this;
			}

			public JAnnotations editable() {
				return JAnnotationsImpl.this;
			}
		};
	}


	//////////////////


	protected void buildAnnotations() {
		if (annotations != null) {
			return;
		}

		Attributes attrs = ((Attributed) node).attributes();
		JAttribute jattr = attrs.getAttribute(RuntimeVisibleAnnotations.NAME);
		RuntimeVisibleAnnotations annos = (RuntimeVisibleAnnotations) jattr.getValue();

		List<JAnnotation> annotations = new ArrayList<JAnnotation>();

		for (org.jackie.jclassfile.attribute.anno.Annotation a : annos.getAnnotations()) {
			JAnnotation anno = new JAnnotationImpl(node(), a);
			annotations.add(anno);
		}

		this.annotations = annotations;
	}


	public void compile() {
		for (JAnnotation ann : getJAnnotations()) {
			CompilableHelper.compile(ann);
		}
	}
}


