package org.jackie.compiler.filemanager.foimpl;

import org.jackie.compiler.filemanager.FileObject;
import static org.jackie.utils.Assert.doAssert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;

/**
 * @author Patrik Beno
 */
public class ByteArrayFileObject implements FileObject {

	protected String pathname;
	protected byte[] bytes;
	protected boolean readonly;

	public ByteArrayFileObject(String pathname) {
		this.pathname = pathname;
		this.bytes = new byte[0];
	}

	public String getPathName() {
		return pathname;
	}

	public Charset getCharset() {
		return null;
	}

	public long getSize() {
		return bytes.length;
	}

	public long getLastModified() {
		return -1;
	}

	public ReadableByteChannel getInputChannel() {
		return Channels.newChannel(new ByteArrayInputStream(bytes));
	}

	public WritableByteChannel getOutputChannel() {

		doAssert(!readonly, "Read-only file obejct: %s", pathname);

		return Channels.newChannel(new ByteArrayOutputStream() {
			public void close() throws IOException {
				super.close();
				ByteArrayFileObject.this.bytes = toByteArray();
//            Log.debug("%s (%s bytes)", pathname, bytes.length);
			}
		});
	}

	public void makeReadOnly() {
		this.readonly = true;
	}
}
