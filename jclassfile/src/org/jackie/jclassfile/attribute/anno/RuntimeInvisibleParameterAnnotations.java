package org.jackie.jclassfile.attribute.anno;

import org.jackie.jclassfile.model.ClassFileProvider;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.attribute.AttributeProvider;

/**
 * @author Patrik Beno
 */
public class RuntimeInvisibleParameterAnnotations extends ParameterAnnotations {

	static public class Provider implements AttributeProvider {
		public String name() {
			return "RuntimeInvisibleParameterAnnotations";
		}
		public AttributeInfo createAttribute(ClassFileProvider owner) {
			return new RuntimeInvisibleParameterAnnotations(owner);
		}
	}

	public RuntimeInvisibleParameterAnnotations(ClassFileProvider owner) {
		super(owner);
	}

}