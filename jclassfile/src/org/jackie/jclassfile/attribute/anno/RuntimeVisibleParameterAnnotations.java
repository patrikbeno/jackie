package org.jackie.jclassfile.attribute.anno;

import org.jackie.jclassfile.model.ClassFileProvider;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.attribute.AttributeProvider;

/**
 * @author Patrik Beno
 */
public class RuntimeVisibleParameterAnnotations extends ParameterAnnotations {

	static public class Provider implements AttributeProvider {
		public String name() {
			return "RuntimeVisibleParameterAnnotations";
		}
		public AttributeInfo createAttribute(ClassFileProvider owner) {
			return new RuntimeVisibleParameterAnnotations(owner);
		}
	}

	public RuntimeVisibleParameterAnnotations(ClassFileProvider owner) {
		super(owner);
	}

}
