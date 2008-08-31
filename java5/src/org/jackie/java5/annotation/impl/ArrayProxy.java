package org.jackie.java5.annotation.impl;

import java.util.List;

/**
 * @author Patrik Beno
 */
class ArrayProxy {

	String clsname;
	List<Object> values;

	public ArrayProxy(String clsname, List<Object> values) {
		this.clsname = clsname;
		this.values = values;
	}

}
