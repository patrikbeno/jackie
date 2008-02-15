package org.jackie.compiler.util;

import org.jackie.compiler.typeregistry.TypeRegistry;
import org.jackie.utils.Assert;

/**
 * @author Patrik Beno
 */
public class Context {

	static public TypeRegistry typeRegistry() {
		throw Assert.notYetImplemented();
	}

	static public ClassLoader annotationClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}
}
