package org.jackie.utils.naming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Patrik Beno
 */
class QDHName implements Name {
	String sname;
	QDHName parent;
	Set<QDHName> children;

	public QDHName() {
		this(null, "");
	}

	public QDHName(QDHName parent, String sname) {
		this.parent = parent;
		this.sname = sname.intern();
		if (parent != null) {
			parent.children().add(this);
		}
	}

	public String getName() {
		StringBuilder sb = new StringBuilder();
		if (parent != null) {
			sb.append(parent.getName()).append(".");
		}
		sb.append(sname);
		return sb.toString();
	}

	public String getShortName() {
		return sname;
	}

	public List<String> getNameComponents() {
		List<String> list = new ArrayList<String>();
		QDHName n = this;
		while (n != null && !n.isRoot()) {
			list.add(n.sname);
			n = n.parent;
		}
		Collections.reverse(list);
		return Collections.unmodifiableList(list);
	}

	private boolean isRoot() {
		return parent == null;
	}

	public Name getParent() {
		return parent;
	}

	public List<Name> getChildren() {
		return Collections.unmodifiableList(new ArrayList<Name>(children));
	}

	Set<QDHName> children() {
		if (children == null) {
			children = new HashSet<QDHName>();
		}
		return children;
	}

	@Override
	public String toString() {
		return getName();
	}

	int hashcode;

	public int hashCode() {
		if (hashcode != 0) {
			return hashcode;
		}
		int result;
		result = sname.hashCode();
		result = 29 * result + (parent != null ? parent.hashCode() : 0);
		hashcode = result;
		return hashcode;
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		QDHName qdhName = (QDHName) o;

		if (parent != null ? !parent.equals(qdhName.parent) : qdhName.parent != null) return false;
		if (!sname.equals(qdhName.sname)) return false;

		return true;
	}
}
