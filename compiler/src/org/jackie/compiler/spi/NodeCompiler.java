package org.jackie.compiler.spi;

/**
 * @author Patrik Beno
 */
public interface NodeCompiler<T> {

	void compile(T context);

}
