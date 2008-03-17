package org.jackie.compiler.typeregistry;

import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.LoadLevel;
import org.jackie.compiler.attribute.KindAttribute;
import org.jackie.compiler.attribute.Kind;
import org.jackie.utils.ClassName;
import org.jackie.jvm.extension.builtin.JPrimitive;

/**
 * @author Patrik Beno
 */
public class PrimitiveTypeRegistry extends AbstractTypeRegistry {

	public PrimitiveTypeRegistry() {
		super(null);
	}

	public boolean hasJClass(ClassName clsname) {
		checkInitialized();
		return classes.containsKey(clsname.getFQName());
	}


	protected void checkInitialized() {
		if (classes.isEmpty()) {
			registerPrimitives();
		}
	}

	protected void registerPrimitives() {
		try {
			setEditable(true);
			for (JPrimitive p : JPrimitive.values()) {
				JClassImpl jclass = new JClassImpl(p.getPrimitiveClass().getName(), null, this);
				jclass.loadLevel = LoadLevel.CODE; // mark as loaded (primitives are unloadable)
				jclass.attributes().edit()
						.addAttribute(KindAttribute.class, new KindAttribute(Kind.PRIMITIVE));

				classes.put(jclass.getFQName(), jclass);
			}
		} finally {
			setEditable(false);
		}
	}
}
