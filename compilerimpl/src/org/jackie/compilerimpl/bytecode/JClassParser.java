package org.jackie.compilerimpl.bytecode;

import org.jackie.compilerimpl.jmodelimpl.LoadLevel;
import org.jackie.context.Service;

import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @author Juraj Burian
 *
 */
public interface JClassParser extends Service {
	
	void execute(InputStream from, LoadLevel loadLevel) throws IOException;

}
