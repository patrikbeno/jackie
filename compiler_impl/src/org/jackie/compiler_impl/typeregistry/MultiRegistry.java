package org.jackie.compiler_impl.typeregistry;

import org.jackie.compiler.typeregistry.TypeRegistry;
import org.jackie.compiler_impl.jmodelimpl.LoadLevel;
import org.jackie.jvm.JClass;
import org.jackie.utils.Assert;
import org.jackie.utils.ClassName;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public class MultiRegistry implements TypeRegistry {

	protected List<TypeRegistry> dependencies;

	public MultiRegistry(TypeRegistry... registries) {
		this(Arrays.asList(registries));
	}

	public MultiRegistry(List<TypeRegistry> dependencies) {
		this.dependencies = dependencies;
	}

	public boolean isEditable() {
		return false;
	}

	public void setEditable(boolean editable) {
		throw Assert.unsupported();
	}

	public boolean hasJClass(ClassName clsname) {
		for (TypeRegistry r : dependencies) {
			if (r.hasJClass(clsname)) {
				return true;
			}
		}
		return false;
	}

	public JClass getJClass(ClassName clsname) {
		for (TypeRegistry r : dependencies) {
			JClass cls = r.getJClass(clsname);
			if (cls != null) {
				return cls;
			}
		}
		return null;
	}

	public JClass getJClass(Class cls) {
		return getJClass(new ClassName(cls));
	}

	public Set<String> getJClassIndex() {
		throw Assert.notYetImplemented(); // todo implement this 
	}

	public Iterable<JClass> jclasses() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public void loadJClass(JClass jclass, LoadLevel level) {
		throw Assert.unsupported(); 
	}

}
