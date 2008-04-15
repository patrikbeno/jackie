package org.jackie.compiler_impl.typeregistry;

import org.jackie.compiler.filemanager.FileManager;
import org.jackie.compiler_impl.util.PathName;
import org.jackie.jvm.JClass;
import org.jackie.utils.Assert;
import org.jackie.utils.ClassName;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public class JClassRegistry extends AbstractTypeRegistry {

	protected Set<String> index;

	public JClassRegistry(FileManager fileManager) {
		super(fileManager);
		rebuildIndex();
	}

	public JClassRegistry(Set<String> index) {
		super(null);
		this.index = new HashSet<String>(index);
	}

	public void rebuildIndex() {
		this.index = extractClassNames(fileManager.getPathNames());
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

	public Iterable<JClass> jclasses() {
		return new Iterable<JClass>() {
			public Iterator<JClass> iterator() {
				return new Iterator<JClass>() {

					Iterator<String> it = index.iterator();

					public boolean hasNext() {
						return it.hasNext();
					}

					public JClass next() {
						return getJClass(new ClassName(it.next()));
					}

					public void remove() {
						throw Assert.unsupported();
					}
				};
			}
		};
	}

}
