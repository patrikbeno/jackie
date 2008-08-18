package org.jackie.jvm.attribute;

import org.jackie.jvm.Editable;
import org.jackie.jvm.JNode;

import java.util.List;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public interface Attributes extends Editable<Attributes.Editor> {

	JNode jnode();

	Set<String> getAttributeNames();

	List<JAttribute> getAttributes();

	JAttribute getAttribute(String name);

	public interface Editor extends org.jackie.jvm.Editor<Attributes> {

		Editor addAttribute(JAttribute attribute);

	}

}
