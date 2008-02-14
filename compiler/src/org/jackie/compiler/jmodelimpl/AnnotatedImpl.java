package org.jackie.compiler.jmodelimpl;

import org.jackie.jmodel.props.Annotated;
import org.jackie.jmodel.structure.JAnnotation;
import org.jackie.utils.Assert;

import java.util.List;
import java.lang.annotation.Annotation;

/**
 * @author Patrik Beno
 */
public class AnnotatedImpl implements Annotated {
	public List<JAnnotation> getJAnnotations() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public List<? extends Annotation> getAnnotations() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public <T extends Annotation> T getAnnotation(Class<T> type) {
		throw Assert.notYetImplemented(); // todo implement this
	}
}
