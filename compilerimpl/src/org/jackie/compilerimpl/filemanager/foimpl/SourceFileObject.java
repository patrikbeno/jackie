package org.jackie.compilerimpl.filemanager.foimpl;

import org.jackie.compiler.filemanager.FileObject;
import org.jackie.utils.Assert;

import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;

/**
 * @author Patrik Beno
 */
public class SourceFileObject implements FileObject {

	protected String pathname;

	protected URL url;

	public SourceFileObject(String pathname, URL url) {
		this.pathname = pathname;
		this.url = url;
	}

	public String getPathName() {
		return pathname;
	}

	public Charset getCharset() {
		return Charset.forName("UTF-8");
	}

	public long getSize() {
		try {
			return url.openConnection().getContentLength();
		} catch (IOException e) {
			throw Assert.notYetHandled(e);
		}
	}

	public long getLastModified() {
		try {
			return url.openConnection().getLastModified();
		} catch (IOException e) {
			throw Assert.notYetHandled(e);
		}
	}

	public ReadableByteChannel getInputChannel() {
		try {
			return Channels.newChannel(url.openStream());
		} catch (IOException e) {
			throw Assert.notYetHandled(e);
		}
	}

	public WritableByteChannel getOutputChannel() {
		throw Assert.unsupported();
	}
}
