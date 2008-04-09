package org.jackie.jvm.attribute;

import org.jackie.jvm.Editable;

import java.util.Set;
import java.util.Map;
import java.util.List;

/**
 * @author Patrik Beno
 */
public interface Attributes extends Editable<Attributes.Editor> {

	Set<String> getAttributeNames();

	List<JAttribute> getAttributes();

	JAttribute getAttribute(String name);

	public interface Editor extends org.jackie.jvm.Editor<Attributes> {

		Editor addAttribute(JAttribute attribute);

	}

}
