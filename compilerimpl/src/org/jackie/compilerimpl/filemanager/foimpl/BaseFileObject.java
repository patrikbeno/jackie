package org.jackie.compilerimpl.filemanager.foimpl;

import org.jackie.compiler.filemanager.FileObject;

/**
 * @author Patrik Beno
 */
public abstract class BaseFileObject implements FileObject {

	protected String pathname;

	protected BaseFileObject(String pathname) {
		this.pathname = pathname;
	}

	public String getPathName() {
		return pathname;
	}

	public String toString() {
		return pathname;
	}
}
