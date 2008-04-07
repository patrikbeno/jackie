package org.jackie.compiler_impl.typeregistry;

import org.jackie.utils.ClassName;
import org.jackie.utils.Assert;
import org.jackie.jvm.JClass;
import org.jackie.compiler.typeregistry.TypeRegistry;

import java.util.Set;

/**
 * @author Patrik Beno
 */
public class CompilerWorkspaceRegistry extends AbstractTypeRegistry {

	protected TypeRegistry workspace;
	protected TypeRegistry dependencies;

	public CompilerWorkspaceRegistry(TypeRegistry workspace, TypeRegistry dependencies) {
		super(null);
		this.workspace = workspace;
		this.dependencies = dependencies;
	}

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

	public Set<String> getJClassIndex() {
		throw Assert.notYetImplemented(); // todo implement this 
	}

	public Iterable<JClass> jclasses() {
		return workspace.jclasses();
	}
}
