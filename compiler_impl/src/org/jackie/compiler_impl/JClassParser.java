package org.jackie.compiler_impl;

import java.io.IOException;
import java.io.InputStream;

import org.jackie.compiler_impl.jmodelimpl.LoadLevel;
import org.jackie.context.Service;

/**
 * 
 * @author Juraj Burian
 *
 */
public interface JClassParser extends Service {
	
	void execute(InputStream from, LoadLevel loadLevel) throws IOException;

}
