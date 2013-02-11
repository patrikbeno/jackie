package org.jackie.java5.annotation;

import org.jackie.jvm.Editable;
import org.jackie.jvm.JClass;
import org.jackie.jvm.extension.Extension;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author Patrik Beno
 */
public interface JAnnotations extends Extension, Editable<JAnnotations.Editor> {

	/**
	 * All annotations attached to the {@link #node()}
	 * @return
	 */
	List<JAnnotation> getJAnnotations();

	/**
	 * Annotation for a given type
	 * @param jclass type of the annotation
	 * @return JAnnotation instance or null if there is no such annotation
	 */
	JAnnotation getJAnnotation(JClass jclass);

	/**
	 * Get native runtime annotation for a given runtime annotation type. 
	 * @param type runtime type of the annotation
	 * @return
	 */
	<T extends Annotation> T getAnnotation(Class<T> type);

	
	public interface Editor extends org.jackie.jvm.Editor<JAnnotations> {

		Editor addAnnotation(JAnnotation annotation);

	}


}
