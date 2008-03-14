package org.jackie.jmodel.attribute;

import org.jackie.jmodel.Editable;

import java.util.Set;
import java.util.Map;

/**
 * @author Patrik Beno
 */
public interface Attributes extends Editable<Attributes.Editor> {

	Set<Class<? extends JAttribute>> getAttributeTypes();

	Map<Class<? extends JAttribute>, JAttribute> getAttributes();

	<T extends JAttribute> T getAttribute(Class<T> type);

	public interface Editor extends org.jackie.jmodel.Editor<Attributes> {

		<T extends JAttribute> Editor addAttribute(Class<T> type, T attribute);

	}

}
