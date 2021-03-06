package org.jackie.compilerimpl.filemanager;

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
				throw Assert.unsupported();
			}
		};
	}

	public FileObject create(String pathname) {
		throw Assert.unsupported();
	}

	public void remove(String pathname) {
		throw Assert.unsupported(); 
	}
}
