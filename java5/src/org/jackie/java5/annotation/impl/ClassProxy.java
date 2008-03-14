package org.jackie.java5.annotation.impl;

import org.jackie.utils.Assert;

/**
 * @author Patrik Beno
 */
public class ClassProxy {

	public final String clsname;


	public ClassProxy(String clsname) {
		this.clsname = clsname;
	}

	public Class asClass() {
		try {
			// fixme wrong classloader
			return Class.forName(clsname, false, Thread.currentThread().getContextClassLoader());
		} catch (ClassNotFoundException e) {
			throw Assert.notYetHandled(e);
		}
	}
}
