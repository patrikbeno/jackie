package org.jackie.jmodel;

import org.jackie.jmodel.structure.JAnnotation;

import java.util.List;
import java.lang.annotation.Annotation;

/**
 * @author Patrik Beno
 */
public interface Annotated {

	List<JAnnotation> getJAnnotations();

	List<? extends Annotation> getAnnotations();

	<T extends Annotation> T getAnnotation(Class<T> type);
}
