package org.jackie.compiler_impl.bytecode;

/**
 * @author Patrik Beno
 */
public interface Compilable<T> {

	void compile(T t);

}
