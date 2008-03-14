package org.jackie.java5.annotation.impl;

import org.jackie.jmodel.JClass;
import org.jackie.jmodel.JNode;
import org.jackie.jmodel.Editable;
import org.jackie.jmodel.structure.JMethod;
import org.jackie.jmodel.structure.JField;
import org.jackie.jmodel.structure.JParameter;
import org.jackie.jmodel.attribute.Attributed;
import org.jackie.jmodel.attribute.Attributes;
import org.jackie.java5.annotation.JAnnotation;
import org.jackie.java5.annotation.Annotations;
import org.jackie.utils.Assert;
import static org.jackie.utils.Assert.typecast;
import static org.objectweb.asm.Type.getType;
import org.objectweb.asm.tree.AnnotationNode;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class AnnotationsImpl implements Annotations {

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
		RuntimeVisibleAnnotationsAttribute rtannos = attrs
				.getAttribute(RuntimeVisibleAnnotationsAttribute.class);

		List<JAnnotation> annotations = new ArrayList<JAnnotation>();

		for (AnnotationNode anode : rtannos.getAnnotationNodes()) {
			JAnnotation anno = new AnnotationImpl(anode, this);
			annotations.add(anno);
		}

		this.annotations = annotations;
	}


}


