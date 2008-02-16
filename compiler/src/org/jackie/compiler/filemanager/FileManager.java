package org.jackie.compiler.filemanager;

import java.util.Iterator;
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
