package org.jackie.jclassfile.attribute.anno;

import org.jackie.jclassfile.attribute.AttributeProvider;
import org.jackie.jclassfile.attribute.AttributeSupport;
import org.jackie.jclassfile.model.AttributeInfo;

/**
 * @author Patrik Beno
 */
public class RuntimeVisibleParameterAnnotations extends ParameterAnnotations {

	static public class Provider implements AttributeProvider {
		public String name() {
			return "RuntimeVisibleParameterAnnotations";
		}
		public AttributeInfo createAttribute(AttributeSupport owner) {
			return new RuntimeVisibleParameterAnnotations(owner);
		}
	}

	public RuntimeVisibleParameterAnnotations(AttributeSupport owner) {
		super(owner);
	}

}
