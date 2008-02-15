package org.jackie.compiler.typeregistry;

import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.jmodelimpl.type.PrimitiveTypeImpl;
import org.jackie.compiler.util.ClassName;
import org.jackie.jmodel.JPrimitive;

/**
 * @author Patrik Beno
 */
public class PrimitiveTypeRegistry extends AbstractTypeRegistry {

	{
		registerPrimitives();
	}

	public boolean hasJClass(ClassName clsname) {
		return classes.containsKey(clsname.getFQName());
	}

	protected void registerPrimitives() {
		for (JPrimitive p : JPrimitive.values()) {
			JClassImpl cls = new JClassImpl();
			cls.name = p.getPrimitiveClass().getName();
			cls.addCapability(new PrimitiveTypeImpl(cls, p));

			classes.put(cls.name, cls);
		}
	}
}
