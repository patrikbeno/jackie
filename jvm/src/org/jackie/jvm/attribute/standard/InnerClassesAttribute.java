package org.jackie.jvm.attribute.standard;

import org.jackie.jvm.attribute.JAttribute;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Patrik Beno
 */
public class InnerClassesAttribute implements JAttribute {

	protected List<String> classes;

	public InnerClassesAttribute(List<String> classes) {
		this.classes = new ArrayList<String>(classes);
	}

	public String getName() {
		return "InnerClasses";
	}

	public List<String> getClasses() {
		return Collections.unmodifiableList(classes);
	}
}