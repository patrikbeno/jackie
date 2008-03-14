package org.jackie.compiler.jmodelimpl.attribute;

import org.jackie.jmodel.attribute.JAttribute;
import org.jackie.jmodel.attribute.Attributes;
import org.jackie.utils.Assert;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

/**
 * @author Patrik Beno
 */
public class AttributesImpl implements Attributes {

	Map<Class<? extends JAttribute>, JAttribute> attributes;

	public Set<Class<? extends JAttribute>> getAttributeTypes() {
		return (attributes == null) ? Collections.emptySet() : (Set) attributes.keySet();
	}

	public Map<Class<? extends JAttribute>, JAttribute> getAttributes() {
		return (attributes == null) ? Collections.emptyMap() : (Map) Collections.unmodifiableMap(attributes);
	}

	public <T extends JAttribute> T getAttribute(Class<T> type) {
		return (attributes == null) ? null : (T) attributes.get(type);
	}

	public boolean isEditable() {
		throw Assert.notYetImplemented(); // todo implement this 
	}

	public Editor edit() {
		return new Editor() {
			public <T extends JAttribute> Editor addAttribute(Class<T> type, T attribute) {
				if (attributes == null) {
					attributes = new HashMap<Class<? extends JAttribute>, JAttribute>();
				}
				attributes.put(type, attribute);
				return this;
			}

			public Attributes editable() {
				return AttributesImpl.this;
			}
		};
	}
}
