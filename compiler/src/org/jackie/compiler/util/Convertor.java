package org.jackie.compiler.util;

/**
 * @author Juraj Burian
 */
public class Convertor<E, F> {

	public E execute(F f) {
		return (E)f;
	}

}
