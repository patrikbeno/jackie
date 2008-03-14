package org.jackie.compiler.typeregistry;

import org.jackie.utils.ClassName;
import org.jackie.compiler.filemanager.FileManager;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public class JClassRegistry extends AbstractTypeRegistry {

	protected Set<String> index;


	public JClassRegistry(FileManager fileManager, Set<String> index) {
		super(fileManager);
		this.index = new HashSet<String>(index);
	}

	public JClassRegistry(Set<String> index) {
		this(null, index);
	}

	public boolean hasJClass(ClassName clsname) {
		assert !clsname.isArray();
		return index.contains(clsname.getFQName());
	}
}
