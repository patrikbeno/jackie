package org.jackie.java5.annotation;

import org.jackie.jvm.Editable;
import org.jackie.jvm.extension.Extension;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author Patrik Beno
 */
public interface JAnnotations extends Extension, Editable<JAnnotations.Editor> {

	List<JAnnotation> getJAnnotations();

	List<? extends Annotation> getAnnotations();

	<T extends Annotation> T getAnnotation(Class<T> type);

	
	public interface Editor extends org.jackie.jvm.Editor<JAnnotations> {

		Editor addAnnotation(JAnnotation annotation);

	}


}
