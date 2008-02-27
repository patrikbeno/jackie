package org.jackie.compiler.bytecode;

/**
 * @author Patrik Beno
 */
public interface Compilable<T> {

	void compile(T t);

}
