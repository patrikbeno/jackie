package org.jackie.compilerimpl.util;

/**
 * @author Patrik Beno
 */
public class PathName {

	String pathname;
	String clsname;

	public PathName(String pathname) {
		this.pathname = pathname;
		if (pathname.endsWith(".class")) {
			this.clsname = pathname.substring(0, pathname.length()-".class".length()).replace('/','.');
		}
	}

	public boolean isClass() {
		return clsname != null;
	}

	public String getClassName() {
		return clsname;
	}

}
