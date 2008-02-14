package org.jackie.compiler.typeregistry;

import org.jackie.compiler.jmodelimpl.JClassImpl;
import org.jackie.compiler.util.ClassName;

/**
 * @author Patrik Beno
 */
public class CompilerWorkspaceRegistry implements TypeRegistry {

	protected TypeRegistry workspace;
	protected TypeRegistry dependencies;

	public boolean hasJClass(ClassName clsname) {
		return dependencies.hasJClass(clsname) || workspace.hasJClass(clsname);
	}

	public JClassImpl getJClass(ClassName clsname) {
		JClassImpl cls;

		cls = dependencies.getJClass(clsname);
		if (cls != null) { return cls; }

		cls = workspace.getJClass(clsname);
		if (cls != null) { return cls; }

		return null;
	}
}
