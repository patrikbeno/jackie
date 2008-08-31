package org.jackie.jclassfile.attribute.anno;

import org.jackie.jclassfile.model.ClassFileProvider;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.attribute.AttributeProvider;

/**
 * @author Patrik Beno
 */
public class RuntimeInvisibleAnnotations extends Annotations {

	static public class Provider implements AttributeProvider {
		public String name() {
			return "RuntimeInvisibleAnnotations";
		}
		public AttributeInfo createAttribute(ClassFileProvider owner) {
			return new RuntimeInvisibleAnnotations(owner);
		}
	}

	public RuntimeInvisibleAnnotations(ClassFileProvider owner) {
		super(owner);
	}
}