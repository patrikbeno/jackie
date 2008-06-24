package org.jackie.compiler.extension;

/**
 * QDH idea: special types can register listeners to be notified about editing changes
 *
 * @author Patrik Beno
 */
public interface Listeners {

	interface ProjectListener {}
	interface ModuleListener {}
	interface PackageListener {}
}
