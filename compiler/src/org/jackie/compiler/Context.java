package org.jackie.compiler;

import org.jackie.compiler.extension.ExtensionManager;
import org.jackie.compiler.typeregistry.TypeRegistry;

/**
 * @author Patrik Beno
 */
public class Context {

	static private Context context;

	static public Context createContext() {
		context = new Context();
		return context;
	}

	static public Context context() {
		return context;
	}

	TypeRegistry typeRegistry;
	ExtensionManager extensionManager;

	{
		extensionManager = new ExtensionManager(); // fixme hardcoded instance
	}


	public TypeRegistry typeRegistry() {
		return typeRegistry;
	}

	public void typeRegistry(TypeRegistry typeregistry) {
		this.typeRegistry = typeregistry;
	}

	public ClassLoader annotationClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	public ExtensionManager extensionSupport() {
		return extensionManager;
	}

}
