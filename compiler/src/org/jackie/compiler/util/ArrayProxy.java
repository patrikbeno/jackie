package org.jackie.compiler.util;

import java.util.List;

/**
 * @author Patrik Beno
 */
public class ArrayProxy {

	String clsname;
	List<Object> values;

	public ArrayProxy(String clsname, List<Object> values) {
		this.clsname = clsname;
		this.values = values;
	}

}
