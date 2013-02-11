package org.jackie.jclassfile.attribute.anno;

import org.jackie.jclassfile.attribute.AttributeProvider;
import org.jackie.jclassfile.attribute.AttributeSupport;
import org.jackie.jclassfile.model.AttributeInfo;

/**
 * @author Patrik Beno
 */
public class RuntimeInvisibleAnnotations extends Annotations {

	static public class Provider implements AttributeProvider {
		public String name() {
			return "RuntimeInvisibleAnnotations";
		}
		public AttributeInfo createAttribute(AttributeSupport owner) {
			return new RuntimeInvisibleAnnotations(owner);
		}
	}

	public RuntimeInvisibleAnnotations(AttributeSupport owner) {
		super(owner);
	}
}