package org.jackie.compiler.spi;

import org.jackie.jvm.JNode;

/**
 * @author Patrik Beno
 */
public class CompilableHelper {

	static public void compile(Object compilable) {
		((Compilable) compilable).compile();
	}

}
