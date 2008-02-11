package org.jackie.utils;

import java.io.Closeable;
import java.io.IOException;

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

}
