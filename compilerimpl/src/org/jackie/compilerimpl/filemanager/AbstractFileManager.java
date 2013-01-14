package org.jackie.compilerimpl.filemanager;

import java.util.Set;
import org.jackie.compiler.filemanager.FileManager;
import org.jackie.compiler.filemanager.FileObject;
import org.jackie.utils.Assert;

import java.util.Iterator;

/**
 * @author Patrik Beno
 */
public abstract class AbstractFileManager implements FileManager {

	interface FileObjectIterable extends Iterable<FileObject>, Iterator<FileObject> {}

	public Iterable<FileObject> getFileObjects() {
		return new FileObjectIterable() {

			Iterator<String> it = getPathNames().iterator();

			public Iterator<FileObject> iterator() {
				return this;
			}

			public boolean hasNext() {
				return it.hasNext();
			}

			public FileObject next() {
				return getFileObject(it.next());
			}

			public void remove() {
				it.remove();
			}
		};
	}

	@Override
	public Set<String> getPathNames() {
		throw new UnsupportedOperationException(); // todo implement this
	}

	@Override
	public FileObject getFileObject(String pathname) {
		throw new UnsupportedOperationException(); // todo implement this
	}

	public FileObject create(String pathname) {
		throw Assert.unsupported();
	}

	public void remove(String pathname) {
		throw Assert.unsupported(); 
	}
}
