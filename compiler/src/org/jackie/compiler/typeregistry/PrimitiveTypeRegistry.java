package org.jackie.compiler.typeregistry;

import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.jmodelimpl.LoadLevel;
import org.jackie.compiler.jmodelimpl.attribute.impl.Kind;
import org.jackie.compiler.jmodelimpl.attribute.impl.KindAttribute;
import org.jackie.compiler.util.ClassName;
import org.jackie.compiler.filemanager.FileManager;
import org.jackie.jmodel.JClass;
import org.jackie.jmodel.extension.builtin.JPrimitive;

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
