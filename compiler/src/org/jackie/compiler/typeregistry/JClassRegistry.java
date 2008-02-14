package org.jackie.compiler.typeregistry;

import org.jackie.compiler.util.ClassName;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public class JClassRegistry extends AbstractTypeRegistry {

	protected Set<String> index;

	public JClassRegistry(Set<String> index) {
		this.index = new HashSet<String>(index);
	}

	public boolean hasJClass(ClassName clsname) {
		assert !clsname.isArray();
		return index.contains(clsname.getFQName());
	}
}
