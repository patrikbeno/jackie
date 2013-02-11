package org.jackie.utils;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

/**
 * @author Patrik Beno
 */
public class IOHelper {

	static public void close(Object... objects) {
		for (Object o : objects) {
			close(o);
		}
	}

	static public void close(Object o) {
		if (o == null) {
			return;
		}
		try {
			if (o instanceof Closeable) {
				((Closeable) o).close();
			}
		} catch (IOException e) {
			throw Assert.notYetHandled(e); // todo ignore exception
		}
	}

	static public void write(ByteBuffer buf, WritableByteChannel ch) {
		try {
			while (buf.hasRemaining()) {
				ch.write(buf);
			}
		} catch (IOException e) {
			throw Assert.notYetHandled(e);
		}
	}

}
