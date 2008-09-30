package org.jackie.jclassfile.attribute.anno;

import org.jackie.jclassfile.attribute.AttributeProvider;
import org.jackie.jclassfile.attribute.AttributeSupport;
import org.jackie.jclassfile.model.AttributeInfo;

/**
 * @author Patrik Beno
 */
public class RuntimeVisibleAnnotations extends Annotations {

	static public final String NAME = RuntimeVisibleAnnotations.class.getSimpleName();

	static public class Provider implements AttributeProvider {
		public String name() {
			return NAME;
		}
		public AttributeInfo createAttribute(AttributeSupport owner) {
			return new RuntimeVisibleAnnotations(owner);
		}
	}

	public RuntimeVisibleAnnotations(AttributeSupport owner) {
		super(owner);
	}
}
