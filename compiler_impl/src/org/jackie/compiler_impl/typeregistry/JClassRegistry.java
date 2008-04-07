package org.jackie.compiler_impl.typeregistry;

import org.jackie.utils.ClassName;
import org.jackie.utils.Assert;
import org.jackie.compiler.filemanager.FileManager;
import org.jackie.compiler_impl.util.PathName;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public class JClassRegistry extends AbstractTypeRegistry {

	protected Set<String> index;

	public JClassRegistry(FileManager fileManager) {
		super(fileManager);
		this.index = extractClassNames(fileManager.getPathNames());
	}

	public JClassRegistry(Set<String> index) {
		super(null);
		this.index = new HashSet<String>(index);
	}


	protected Set<String> extractClassNames(Set<String> pathnames) {
		Set<String> classes = new HashSet<String>(pathnames.size());
		for (String s : pathnames) {
			PathName pathname = new PathName(s);
			if (pathname.isClass()) {
				classes.add(pathname.getClassName());
			}
		}
		return classes;
	}

	public boolean hasJClass(ClassName clsname) {
		assert !clsname.isArray();
		return index.contains(clsname.getFQName());
	}

	public Set<String> getJClassIndex() {
		throw Assert.notYetImplemented(); // todo implement this 
	}
}
