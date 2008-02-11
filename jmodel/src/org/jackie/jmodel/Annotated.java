package org.jackie.jmodel;

import org.jackie.jmodel.structure.JAnnotation;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author Patrik Beno
 */
public interface Annotated {

	List<JAnnotation> getJAnnotations();

	List<? extends Annotation> getAnnotations();

	<T extends Annotation> T getAnnotation(Class<T> type);
}
