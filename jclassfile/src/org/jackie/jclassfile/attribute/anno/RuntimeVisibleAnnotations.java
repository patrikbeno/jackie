package org.jackie.jclassfile.attribute.anno;

import org.jackie.jclassfile.model.ClassFileProvider;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.attribute.AttributeProvider;

/**
 * @author Patrik Beno
 */
public class RuntimeVisibleAnnotations extends Annotations {

	static public class Provider implements AttributeProvider {
		public String name() {
			return "RuntimeVisibleAnnotations";
		}
		public AttributeInfo createAttribute(ClassFileProvider owner) {
			return new RuntimeVisibleAnnotations(owner);
		}
	}

	public RuntimeVisibleAnnotations(ClassFileProvider owner) {
		super(owner);
	}
}
