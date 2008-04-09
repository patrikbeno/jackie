package org.jackie.compiler_impl.jmodelimpl.attribute;

import org.jackie.jvm.attribute.JAttribute;
import org.jackie.jvm.attribute.Attributes;
import org.jackie.utils.Assert;
import static org.jackie.utils.CollectionsHelper.iterable;

import java.util.Set;
import java.util.Collections;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;
import static java.util.Collections.unmodifiableSet;
import static java.util.Collections.unmodifiableList;

/**
 * @author Patrik Beno
 */
public class AttributesImpl implements Attributes {

	List<JAttribute> attributes;

	public Set<String> getAttributeNames() {
		Set<String> names = new HashSet<String>();
		for (JAttribute a : iterable(attributes)) {
			names.add(a.getName());
		}
		return names;
	}

	public List<JAttribute> getAttributes() {
		return attributes != null ? unmodifiableList(attributes) : Collections.<JAttribute>emptyList();
	}

	public JAttribute getAttribute(String name) {
		for (JAttribute a : iterable(attributes)) {
			if (a.getName().equals(name)) {
				return a;
			}
		}
		return null;
	}

	public boolean isEditable() {
		throw Assert.notYetImplemented(); // todo implement this 
	}

	public Editor edit() {
		return new Editor() {
			public Editor addAttribute(JAttribute attribute) {
				JAttribute a = getAttribute(attribute.getName());
				if (attributes == null) {
					attributes = new ArrayList<JAttribute>();
				}
				if (a != null) {
					a.edit().setNext(attribute);
				} else {
					attributes.add(attribute);
				}
				return this;
			}

			public Attributes editable() {
				return AttributesImpl.this;
			}
		};
	}
}
