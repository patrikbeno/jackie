package org.jackie.compilerimpl.jmodelimpl;

import org.jackie.compiler.typeregistry.TypeRegistry;
import org.jackie.jvm.JClass;
import org.jackie.jvm.JPackage;
import org.jackie.jvm.spi.AbstractJNode;
import org.jackie.utils.Stack;
import org.jackie.utils.Assert;
import static org.jackie.utils.Assert.doAssert;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public class JPackageImpl extends AbstractJNode implements JPackage {

	protected TypeRegistry typeRegistry;

	public String name;

	public Set<JPackage> subpackages;

	public Set<JClass> classes;

	{
		subpackages = new HashSet<JPackage>();
		classes = new HashSet<JClass>();
	}

	public JPackageImpl(JPackage owner) {
		super(owner);
	}

	public String getName() {
		return name;
	}

	public String getFQName() {
		if (isDefault()) { return null; }

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
		return getParentPackage() == null;
	}

	public JPackage getParentPackage() {
		return (JPackage) owner();
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
				if (isDefault()) {
					Assert.expected(null, name, "Cannot rename default package!");
				}
				JPackageImpl.this.name = name;
				return this;
			}

			public Editor setParent(JPackage parent) {
				doAssert(!isDefault(), "Cannot relocate (set aprent) to default package!");
				JPackageImpl.this.owner = parent;
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

	public String toString() {
		return isDefault() ? "<default>" : getFQName();
	}
}
