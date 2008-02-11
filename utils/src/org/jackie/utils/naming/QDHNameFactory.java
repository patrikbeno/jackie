package org.jackie.utils.naming;

import org.jackie.utils.Assert;

import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * @author Patrik Beno
 */
public class QDHNameFactory implements NameFactory<QDHName> {

	String separators;
	Set<QDHName> root;

	// fixme use softrefs
	Map<Object, QDHName> roots = new IdentityHashMap<Object, QDHName>();
	Map<String, QDHName> nameCache = new HashMap<String, QDHName>();

	public QDHNameFactory(String separators) {
		this.separators = separators;
		this.root = new HashSet<QDHName>();
	}

	public QDHName getName(String fqname) {

		QDHName name = nameCache.get(fqname);
		if (name != null) {
			// return cached
			return name;
		}

		// not in cache:

		// parse
		String[] components = tokenize(fqname);
		name = null;
		for (String s : components) {
			name = findOrCreate(name, s);
		}
		nameCache.put(fqname, name);
		return name;
	}

	public QDHName getName(QDHName parent, String name) {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public QDHName findOrCreate(QDHName parent, String name) {
		for (QDHName n : (parent != null) ? parent.children() : root) {
			if (name.equals(n.getShortName())) {
				return n;
			}
		}
		QDHName n = new QDHName(parent, name);
		if (parent == null) {
			root.add(n);
		}
		return n;
	}

	private String[] tokenize(String s) {
		StringTokenizer st = new StringTokenizer(s, "/.");
		String[] result = new String[st.countTokens()];
		for (int i = 0; i < result.length; i++) {
			result[i] = st.nextToken();
		}
		return result;
	}

}
