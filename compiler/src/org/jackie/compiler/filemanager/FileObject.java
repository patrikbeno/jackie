package org.jackie.compiler.filemanager;

import java.nio.charset.Charset;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @author Patrik Beno
 */
public interface FileObject {

	/**
	 * path name, slash separated, relative to root (managed by FileManager who provided this
	 * object)
	 *
	 * @return
	 */
	String getPathName();

	/**
	 * null for binary files
	 */
	Charset getCharset();

	/**
	 * -1 of not known
	 *
	 * @return
	 */
	long getSize();

	/**
	 * -1 if unknown
	 *
	 * @return
	 */
	long getLastModified();


	/**
	 * null if not readable
	 *
	 * @return
	 */
	ReadableByteChannel getInputChannel();

	/**
	 * null if not writable
	 *
	 * @return
	 */
	WritableByteChannel getOutputChannel();


}
