package org.jackie.compiler.util;

import static org.jackie.compiler.util.Context.context;
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
			return Class.forName(clsname, false, context().annotationClassLoader());
		} catch (ClassNotFoundException e) {
			throw Assert.notYetHandled(e);
		}
	}
}
