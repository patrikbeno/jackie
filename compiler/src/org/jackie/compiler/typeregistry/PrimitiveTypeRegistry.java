package org.jackie.compiler.typeregistry;

import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.jmodelimpl.attribute.impl.Kind;
import org.jackie.compiler.jmodelimpl.attribute.impl.KindAttribute;
import org.jackie.compiler.util.ClassName;
import org.jackie.jmodel.JClass;
import org.jackie.jmodel.extension.builtin.JPrimitive;

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
			JClass jclass = new JClassImpl();

			jclass.edit()
					.setName(p.getPrimitiveClass().getName());
			jclass.attributes().edit()
					.addAttribute(KindAttribute.class, new KindAttribute(Kind.PRIMITIVE));

			classes.put(jclass.getFQName(), jclass);
		}
	}
}
