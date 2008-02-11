package org.jackie.compiler.filemanager;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.jar.JarInputStream;
import java.util.jar.JarEntry;
import java.net.URL;
import java.io.InputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

import org.jackie.utils.CyclicBuffer;
import org.jackie.utils.Assert;

/**
 * @author Patrik Beno
 */
public class JarFileManager implements FileManager {

	protected URL url;

	protected Map<String, FileObject> fileobjects;

	{
		init();
	}

	public JarFileManager(URL url) {
		this.url = url;
		loadIndex();
	}

	protected void init() {
		fileobjects = new HashMap<String, FileObject>();
	}

	protected void loadIndex() {
		try {
			JarInputStream jin = new JarInputStream(url.openConnection().getInputStream());
			JarEntry je;
			while ((je = jin.getNextJarEntry()) != null) {
				String pathname = je.getName();
				FileObject fo = load(pathname, jin);
				fileobjects.put(je.getName(), fo);
			}
		} catch (IOException e) {
			throw Assert.notYetHandled(e);
		}
	}

	public URL getURL() {
		return url;
	}

	public Set<String> getPathNames() {
		return fileobjects.keySet();
	}

	public FileObject getFileObject(String pathname) {
		FileObject found = fileobjects.get(pathname);
		return found;
	}

	public FileObject create(String pathname) {
		throw Assert.unsupported();
	}

	public Collection<FileObject> getFileObjects() {
		return fileobjects.values();
	}

	protected FileObject load(String name, InputStream in) throws IOException {
		ByteArrayFileObject fo = new ByteArrayFileObject(name);
		WritableByteChannel dst = fo.getOutputChannel();

		ReadableByteChannel cin = Channels.newChannel(in);
		CyclicBuffer buf = new CyclicBuffer();

		int read;
		do {
			read = buf.readFrom(cin);
			buf.writeTo(dst);
		} while (read != -1);

		dst.close();

		fo.makeReadOnly();

		return fo;
	}
}
