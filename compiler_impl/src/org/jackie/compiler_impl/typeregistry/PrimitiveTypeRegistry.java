package org.jackie.compiler_impl.typeregistry;

import org.jackie.compiler_impl.jmodelimpl.JClassImpl;
import org.jackie.compiler_impl.jmodelimpl.LoadLevel;
import org.jackie.compiler_impl.jmodelimpl.type.PrimitiveTypeImpl;
import org.jackie.jvm.JClass;
import org.jackie.jvm.extension.builtin.JPrimitive;
import org.jackie.utils.Assert;
import org.jackie.utils.ClassName;

import java.util.Set;

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

	public Set<String> getJClassIndex() {
		throw Assert.notYetImplemented(); // todo implement this 
	}

	public Iterable<JClass> jclasses() {
		throw Assert.notYetImplemented(); // todo implement this
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
				jclass.extensions().edit().add(new PrimitiveTypeImpl(jclass));
				register(jclass);
			}
		} finally {
			setEditable(false);
		}
	}
}
