package org.jackie.compiler.bytecode;

import java.io.IOException;
import java.io.InputStream;

import org.jackie.compiler.LoadLevel;
import org.jackie.context.Service;

/**
 * 
 * @author Juraj Burian
 *
 */
public interface JClassParser extends Service {
	
	void execute(InputStream from, LoadLevel loadLevel) throws IOException;

}
