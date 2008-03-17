package org.jackie.compiler_impl.filemanager;

import java.util.Set;

/**
 * @author Patrik Beno
 */
public interface FileManager {

	Set<String> getPathNames();

	FileObject getFileObject(String pathname);

	FileObject create(String pathname);

	Iterable<FileObject> getFileObjects();

}
