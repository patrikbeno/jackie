package org.jackie.compiler.filemanager;

import java.nio.charset.Charset;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @author Patrik Beno
 */
public abstract class FileObjectDelegate implements FileObject {

	FileObject fo;

	public FileObjectDelegate(FileObject fo) {
		this.fo = fo;
	}

	public String getPathName() {
		return fo.getPathName();
	}

	public Charset getCharset() {
		return fo.getCharset();
	}

	public long getSize() {
		return fo.getSize();
	}

	public long getLastModified() {
		return fo.getLastModified();
	}

	public ReadableByteChannel getInputChannel() {
		return fo.getInputChannel();
	}

	public WritableByteChannel getOutputChannel() {
		return fo.getOutputChannel();
	}
}
