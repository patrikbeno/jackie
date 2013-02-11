package org.jackie.jclassfile.attribute.anno;

import org.jackie.jclassfile.attribute.AttributeProvider;
import org.jackie.jclassfile.attribute.AttributeSupport;
import org.jackie.jclassfile.model.AttributeInfo;

/**
 * @author Patrik Beno
 */
public class RuntimeInvisibleParameterAnnotations extends ParameterAnnotations {

	static public class Provider implements AttributeProvider {
		public String name() {
			return "RuntimeInvisibleParameterAnnotations";
		}
		public AttributeInfo createAttribute(AttributeSupport owner) {
			return new RuntimeInvisibleParameterAnnotations(owner);
		}
	}

	public RuntimeInvisibleParameterAnnotations(AttributeSupport owner) {
		super(owner);
	}

}