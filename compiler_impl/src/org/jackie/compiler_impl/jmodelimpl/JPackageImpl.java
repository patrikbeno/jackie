package org.jackie.compiler_impl.jmodelimpl;

import org.jackie.jvm.JPackage;
import org.jackie.jvm.JClass;
import org.jackie.utils.Stack;
import org.jackie.compiler.TypeRegistry;

import java.util.HashSet;
import java.util.Set;
import java.util.Collections;

/**
 * @author Patrik Beno
 */
public class JPackageImpl implements JPackage {

	protected TypeRegistry typeRegistry;

	public JPackage parent;

	public String name;

	public Set<JPackage> subpackages;

	public Set<JClass> classes;

	{
		subpackages = new HashSet<JPackage>();
		classes = new HashSet<JClass>();
	}

	public String getName() {
		return name;
	}

	public String getFQName() {
		Stack<String> stack = new Stack<String>();
		for (JPackage p = this; !p.isDefault(); p = p.getParentPackage()) {
			stack.push(p.getName());
		}
		StringBuilder sb = new StringBuilder();
		while (!stack.isEmpty()) {
			sb.append(stack.pop()).append( stack.isEmpty() ? "" : "." );
		}
		return sb.toString();
	}

	public boolean isDefault() {
		return getParentPackage() == null && getName().equals("");
	}

	public JPackage getParentPackage() {
		return parent;
	}

	public Set<JPackage> getSubPackages() {
		return Collections.unmodifiableSet(subpackages);
	}

	public Set<JClass> getClasses() {
		return Collections.unmodifiableSet(classes);
	}

	public boolean isEditable() {
		return typeRegistry.isEditable();
	}

	public Editor edit() {
		return new Editor() {
			public Editor setName(String name) {
				JPackageImpl.this.name = name;
				return this;
			}

			public Editor setParent(JPackage parent) {
				JPackageImpl.this.parent = parent;
				return this;
			}

			public Editor addClass(JClass jclass) {
				classes.add(jclass);
				return this;
			}

			public JPackage editable() {
				return JPackageImpl.this;
			}
		};
	}

}
