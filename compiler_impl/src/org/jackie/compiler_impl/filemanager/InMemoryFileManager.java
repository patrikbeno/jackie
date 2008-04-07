package org.jackie.compiler_impl.filemanager;

import org.jackie.compiler_impl.filemanager.foimpl.ByteArrayFileObject;
import static org.jackie.utils.Assert.doAssert;
import org.jackie.compiler.filemanager.FileManager;
import org.jackie.compiler.filemanager.FileObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public class InMemoryFileManager extends AbstractFileManager implements FileManager {

	protected Map<String, FileObject> artifacts;

	{
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

	public void remove(String pathname) {
		artifacts.remove(pathname);
	}
}
