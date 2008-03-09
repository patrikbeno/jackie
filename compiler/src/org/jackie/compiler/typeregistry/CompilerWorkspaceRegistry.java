package org.jackie.compiler.typeregistry;

import org.jackie.compiler.util.ClassName;
import org.jackie.jmodel.JClass;

/**
 * @author Patrik Beno
 */
public class CompilerWorkspaceRegistry extends AbstractTypeRegistry {

	protected TypeRegistry workspace;
	protected TypeRegistry dependencies;

	public boolean hasJClass(ClassName clsname) {
		return dependencies.hasJClass(clsname) || workspace.hasJClass(clsname);
	}

	public JClass getJClass(ClassName clsname) {
		JClass cls;

		cls = dependencies.getJClass(clsname);
		if (cls != null) { return cls; }

		cls = workspace.getJClass(clsname);
		if (cls != null) { return cls; }

		return null;
	}
}
