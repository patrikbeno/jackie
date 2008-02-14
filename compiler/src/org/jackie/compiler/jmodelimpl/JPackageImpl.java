package org.jackie.compiler.jmodelimpl;

import org.jackie.jmodel.props.FQNamed;

import java.util.List;
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
		return parent.getFQName() + "." + name;
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


}
