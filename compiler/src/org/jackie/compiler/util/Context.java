package org.jackie.compiler.util;

import org.jackie.compiler.jmodelimpl.type.SpecialTypes;
import org.jackie.compiler.typeregistry.TypeRegistry;
import org.jackie.utils.Assert;
import org.jackie.utils.JackieException;
import static org.jackie.utils.Assert.doAssert;

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
	SpecialTypes specialTypes;
	boolean editable;

	{
		specialTypes = new SpecialTypes(); // fixme hardcoded instance
		editable = true;
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

	public SpecialTypes specialtypes() {
		return specialTypes;
	}

	public void assertEditable() {
		if (!editable) {
			throw new JackieException("Model not editable!");
		}
	}
}
