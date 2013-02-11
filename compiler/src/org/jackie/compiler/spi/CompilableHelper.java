package org.jackie.compiler.spi;

/**
 * @author Patrik Beno
 */
public class CompilableHelper {

	static public void compile(Object compilable) {
		((Compilable) compilable).compile();
	}

}
