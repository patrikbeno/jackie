package org.jackie.compiler.filemanager;

import static org.jackie.utils.Assert.doAssert;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public class InMemoryFileManager implements FileManager {

	protected Map<String, FileObject> artifacts;

	{
		init();
	}

	protected void init() {
		artifacts = new HashMap<String, FileObject>();
	}

	public Set<String> getPathNames() {
		return artifacts.keySet();
	}

	public FileObject getFileObject(String pathname) {
		return artifacts.get(pathname);
	}

	public FileObject create(String pathname) {
		doAssert(!artifacts.containsKey(pathname), "Already exists: %s", pathname);
		FileObject fobject = new ByteArrayFileObject(pathname);
		artifacts.put(pathname, fobject);
		return fobject;
	}
}
