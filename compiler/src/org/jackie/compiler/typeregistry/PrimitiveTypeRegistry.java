package org.jackie.compiler.typeregistry;

import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.jmodelimpl.type.PrimitiveTypeImpl;
import org.jackie.compiler.jmodelimpl.type.SpecialTypeImpl;
import org.jackie.compiler.util.ClassName;
import org.jackie.jmodel.JPrimitive;
import org.jackie.jmodel.type.PrimitiveType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Patrik Beno
 */
public class PrimitiveTypeRegistry extends AbstractTypeRegistry {

	Map<String, JClassImpl> classes;
	ArrayRegistry arrays;

	{
		classes = new HashMap<String, JClassImpl>();
		arrays = new ArrayRegistry(this);
		registerPrimitives();
	}


	public boolean hasJClass(ClassName clsname) {
		return classes.containsKey(clsname.getFQName());
	}

	public JClassImpl getJClass(ClassName clsname) {
		return classes.get(clsname.getFQName());
	}

	protected void registerPrimitives() {
		for (JPrimitive p : JPrimitive.values()) {
			JClassImpl cls = new JClassImpl();
			cls.name = p.name();
			cls.addCapability(new PrimitiveTypeImpl(cls, p));

			classes.put(cls.name, cls);
		}
	}
}
