package org.jackie.compiler.jmodelimpl;

import org.jackie.jmodel.props.FQNamed;
import org.jackie.utils.Stack;

import java.util.Set;

/**
 * @author Patrik Beno
 */
public class JPackageImpl implements FQNamed {

	public JPackageImpl parent;

	public String name;

	public Set<JPackageImpl> subpackages;

	public Set<JClassImpl> classes;


	public String getFQName() {
		Stack<String> stack = new Stack<String>();
		for (JPackageImpl p = this; !p.isDefault(); p = p.parent) {
			stack.push(p.name);
		}
		StringBuilder sb = new StringBuilder();
		while (!stack.isEmpty()) {
			sb.append(stack.pop()).append( stack.isEmpty() ? "" : "." );
		}
		return sb.toString();
	}

	public JPackageImpl addSubPackage(JPackageImpl pckg) {
		if (pckg.parent != null) {
			// remove
			pckg.parent.subpackages.remove(pckg);
		}
		subpackages.add(pckg);
		pckg.parent = this;

		return this;
	}

	public JPackageImpl addClass(JClassImpl jclass) {
		if (jclass.jpackage != null) {
			jclass.jpackage.classes.remove(jclass);
		}
		classes.add(jclass);
		jclass.jpackage = this;

		return this;
	}


	public boolean isDefault() {
		return name.isEmpty();
	}
}
