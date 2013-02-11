package org.jackie.compilerimpl.typeregistry;

import org.jackie.compiler.typeregistry.TypeRegistry;
import org.jackie.jvm.JClass;
import org.jackie.utils.Assert;
import org.jackie.utils.ClassName;

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

        this.workspace.setEditable(true);
        setEditable(true);
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

    @Override
    public void register(ClassName clsname) {
        workspace.register(clsname);
    }

    public Set<String> getJClassIndex() {
		throw Assert.notYetImplemented(); // todo implement this 
	}

	public Iterable<JClass> jclasses() {
		return workspace.jclasses();
	}
}
