package org.jackie.compiler.util;

import org.jackie.compiler.jmodelimpl.type.SpecialTypes;
import org.jackie.compiler.typeregistry.TypeRegistry;

/**
 * @author Patrik Beno
 */
public class Context {

	static private Context context;

	static public Context createContext(TypeRegistry typeRegistry) {
		context = new Context(typeRegistry);
		return context;
	}

	static public Context context() {
		return context;
	}

	TypeRegistry typeRegistry;
	SpecialTypes specialTypes;

	{
		specialTypes = new SpecialTypes(); // fixme hardcoded instance
	}

	protected Context(TypeRegistry typeRegistry) {
		this.typeRegistry = typeRegistry;
	}

	public TypeRegistry typeRegistry() {
		return typeRegistry;
	}

	public ClassLoader annotationClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	public SpecialTypes specialtypes() {
		return specialTypes;
	}
}
