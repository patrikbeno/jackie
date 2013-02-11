package org.jackie.compiler.context;

import org.jackie.context.ContextObject;

/**
 * @author Patrik Beno
 */
public class CompilerContext implements ContextObject {

	public Workspace workspace;

	{
		workspace = new Workspace();
	}

}
