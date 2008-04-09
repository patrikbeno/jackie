package org.jackie.java5.annotation.impl;

import org.jackie.jvm.JClass;
import org.jackie.jvm.JNode;
import org.jackie.jvm.Editable;
import org.jackie.jvm.structure.JMethod;
import org.jackie.jvm.structure.JField;
import org.jackie.jvm.structure.JParameter;
import org.jackie.jvm.attribute.Attributed;
import org.jackie.jvm.attribute.Attributes;
import org.jackie.jvm.attribute.JAttribute;
import org.jackie.java5.annotation.JAnnotation;
import org.jackie.java5.annotation.Annotations;
import org.jackie.utils.Assert;
import static org.jackie.utils.Assert.typecast;
import org.jackie.compiler.spi.Compilable;
import static org.objectweb.asm.Type.getType;
import org.objectweb.asm.tree.AnnotationNode;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class AnnotationsImpl implements Annotations, Compilable {

	JNode node;

	List<JAnnotation> annotations;

	public AnnotationsImpl(JNode node) {
		this.node = node;
	}

	public JNode node() {
		return node;
	}

	public List<JAnnotation> getJAnnotations() {
		buildJAnnotations();
		return Collections.unmodifiableList(annotations);
	}

	public List<? extends Annotation> getAnnotations() {
		buildJAnnotations();
		List<Annotation> proxies = new ArrayList<Annotation>();
		for (JAnnotation anno : annotations) {
			AnnotationImpl impl = typecast(anno, AnnotationImpl.class);
			proxies.add(impl.proxy());
		}
		return proxies;
	}

	public <T extends Annotation> T getAnnotation(Class<T> type) {
		assert type != null;
		buildJAnnotations();

		for (JAnnotation anno : annotations) {
			if (type.getName().equals(anno.getJAnnotationType().node().getFQName())) {
				AnnotationImpl impl = typecast(anno, AnnotationImpl.class);
				return typecast(impl.proxy(), type);
			}
		}
		return null;
	}


	/// Editable ///


	public boolean isEditable() {
		return node() instanceof Editable && ((Editable) node()).isEditable();
	}

	public Editor edit() {
//		Helper.assertEditable(findJClass());
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

	protected JClass findJClass() {
		// todo find something better (less if/else, more robustness)
		if (node() instanceof JClass) {
			return (JClass) node();
		} else if (node() instanceof JMethod) {
			return ((JMethod) node()).getJClass();
		} else if (node() instanceof JField) {
			return ((JField) node()).getType();
		} else if (node() instanceof JParameter) {
			return ((JParameter) node()).scope().getJClass();
		} else {
			throw Assert.invariantFailed("cannot find owning JClass for %s", this);
		}
	}

	protected void buildJAnnotations() {
		if (annotations != null) {
			return;
		}

		Attributes attrs = typecast(node, Attributed.class).attributes();

		List<JAnnotation> annotations = new ArrayList<JAnnotation>();

		for (JAttribute attr : (Iterable<JAttribute>) attrs.getAttribute("RuntimeVisibleAnnotations")) {
			assert attr.getValue() instanceof AnnotationNode;
			JAnnotation anno = new AnnotationImpl((AnnotationNode) attr.getValue(), this);
			annotations.add(anno);
		}

		this.annotations = annotations;
	}


	public void compile() {
		for (JAnnotation ann : getJAnnotations()) {
			// todo implement
		}
	}
}


