package org.jackie.compiler.javacintegration;

import org.jackie.compiler.filemanager.FileObject;

import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.nio.channels.Channels;
import java.nio.charset.Charset;

/**
 * @author Patrik Beno
 */
public class JFO extends SimpleJavaFileObject {

	protected FileObject fobject;

	public JFO(FileObject fobject, Kind kind) {
		super(URI.create(fobject.getPathName()), kind);
		this.fobject = fobject;
	}

	public InputStream openInputStream() throws IOException {
		return Channels.newInputStream(fobject.getInputChannel());
	}

	public OutputStream openOutputStream() throws IOException {
		return Channels.newOutputStream(fobject.getOutputChannel());
	}

	public Reader openReader(boolean ignoreEncodingErrors) throws IOException {
		return Channels.newReader(fobject.getInputChannel(), selectCharset(fobject).newDecoder(), -1);
	}

	public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
		Reader reader = openReader(ignoreEncodingErrors);
		char[] chars = new char[(int) fobject.getSize()];
		int pos = 0;
		while (pos < chars.length) {
			int read = reader.read(chars, pos, chars.length - pos);
			if (read == -1) {
				break;
			}
			pos += read;
		}
		return new String(chars);

//      CharBuffer cbuf = CharBuffer.allocate((int) fobject.getSize());
//      while(reader.read(cbuf) != -1 && cbuf.limit() < fobject.getSize());
//      cbuf.rewind();
//      return cbuf;
	}

	public Writer openWriter() throws IOException {
		return Channels
				.newWriter(fobject.getOutputChannel(), selectCharset(fobject).newEncoder(), -1);
	}

	public long getLastModified() {
		return fobject.getLastModified();
	}

	///

	protected Charset selectCharset(FileObject fobject) {
		Charset charset = fobject.getCharset();
		return (charset != null) ? charset : Charset.defaultCharset();
	}
}
