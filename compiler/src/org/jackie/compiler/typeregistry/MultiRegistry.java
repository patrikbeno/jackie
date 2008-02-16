package org.jackie.compiler.typeregistry;

import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.util.ClassName;

import java.util.Arrays;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class MultiRegistry extends AbstractTypeRegistry {

	protected List<TypeRegistry> dependencies;

	public MultiRegistry(TypeRegistry ... registries) {
		this(Arrays.asList(registries));
	}

	public MultiRegistry(List<TypeRegistry> dependencies) {
		this.dependencies = dependencies;
	}

	public boolean hasJClass(ClassName clsname) {
		for (TypeRegistry r : dependencies) {
			if (r.hasJClass(clsname)) {
				return true;
			}
		}
		return false;
	}

	public JClassImpl getJClass(ClassName clsname) {
		for (TypeRegistry r : dependencies) {
			JClassImpl cls = r.getJClass(clsname);
			if (cls != null) {
				return cls;
			}
		}
		return null;
	}
}
