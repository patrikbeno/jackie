package org.jackie.compiler.util;

/**
 * @author Patrik Beno
 */
public class PackageName {

	String fqname;

	PackageName parent;
	String name;

	public PackageName(String fqname) {
		this.fqname = fqname;
	}

	public String getFQName() {
		return fqname;
	}

	public String getName() {
		if (name != null) {
			return name;
		}

		int i = fqname.lastIndexOf('.');

		name = fqname.substring(i+1);
		return name;
	}

	public PackageName getParent() {
		int offset = fqname.length() - getName().length() - 1;
		if (offset > 0) {
			parent = new PackageName(fqname.substring(0, offset));
		}
		return parent;
	}

}
