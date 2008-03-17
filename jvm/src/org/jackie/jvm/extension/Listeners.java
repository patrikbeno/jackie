package org.jackie.jvm.extension;

/**
 * QDH idea: special types can register listeners to be notified about editing changes
 *
 * @author Patrik Beno
 */
public interface Listeners {

	interface ProjectListener {}
	interface ModuleListener {}
	interface PackageListener {}
	interface ClassListener {}
	interface FieldListener {}
	interface MethodListener {}
	interface AnnotationListener {}
	interface AttributeListener {}

}
