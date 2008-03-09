package org.jackie.compiler.jmodelimpl.attribute;

import org.jackie.jmodel.attribute.Attributed;
import org.jackie.jmodel.attribute.JAttribute;
import org.jackie.jmodel.attribute.Attributes;
import org.jackie.utils.Assert;
import static org.jackie.compiler.util.Helper.assertEditable;

import java.util.Set;
import java.util.Map;

/**
 * @author Patrik Beno
 */
public class AttributesImpl implements Attributes {

	public Set<Class<? extends JAttribute>> getAttributeTypes() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public Map<Class<? extends JAttribute>, JAttribute> getAttributes() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public <T extends JAttribute> T getAttribute(Class<T> type) {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public Editor edit() {
		return new Editor() {
			public <T extends JAttribute> Editor addAttribute(Class<T> type, T attribute) {
				throw Assert.notYetImplemented(); // todo implement this
			}

			public Attributes editable() {
				return AttributesImpl.this;
			}
		};
	}
}
