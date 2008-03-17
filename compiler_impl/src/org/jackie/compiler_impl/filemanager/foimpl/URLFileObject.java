package org.jackie.compiler_impl.filemanager.foimpl;

import org.jackie.compiler.filemanager.FileObject;
import org.jackie.utils.Assert;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;

/**
 * @author Patrik Beno
 */
public class URLFileObject implements FileObject {

	protected URL base;
	protected String pathname;

	public URLFileObject(URL base, String pathname) {
		this.base = base;
		this.pathname = pathname;
	}

	public String getPathName() {
		return pathname;
	}

	public Charset getCharset() {
		return null;
	}

	public long getSize() {
		return con().getContentLength();
	}

	public long getLastModified() {
		return con().getLastModified();
	}

	public ReadableByteChannel getInputChannel() {
		try {
			return Channels.newChannel(url().openStream());
		} catch (IOException e) {
			throw Assert.notYetHandled(e);
		}
	}

	public WritableByteChannel getOutputChannel() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	protected URL url() {
		try {
			return new URL(base, pathname);
		} catch (MalformedURLException e) {
			throw Assert.notYetHandled(e);
		}
	}

	URLConnection con() {
		try {
			return url().openConnection();
		} catch (IOException e) {
			throw Assert.notYetHandled(e);
		}
	}

}
