package org.jackie.compiler.filemanager;

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
public class JarEntryFileObject implements FileObject {

	String baseurl;
	String pathname;

	Long size;
	Long lastmodified;

	public JarEntryFileObject(String baseurl, String pathname) {
		this.baseurl = baseurl;
		this.pathname = pathname;
	}

	public String getPathName() {
		return pathname;
	}

	public Charset getCharset() {
		return null;
	}

	public long getSize() {
		return (size != null) ? size : (size = (long) con().getContentLength());
	}

	public long getLastModified() {
		return (lastmodified != null) ? lastmodified : (lastmodified = con().getLastModified());
	}

	public ReadableByteChannel getInputChannel() {
		try {
			return Channels.newChannel(url().openStream());
		} catch (IOException e) {
			throw Assert.notYetHandled(e);
		}
	}

	public WritableByteChannel getOutputChannel() {
		throw Assert.unsupported();
	}

	URL url() {
		try {
			return new URL("jar:"+baseurl+"!/"+pathname);
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
