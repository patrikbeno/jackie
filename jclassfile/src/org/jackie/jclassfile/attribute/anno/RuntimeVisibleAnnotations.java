package org.jackie.jclassfile.attribute.anno;

import org.jackie.jclassfile.attribute.AttributeProvider;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.model.ClassFileProvider;

/**
 * @author Patrik Beno
 */
public class RuntimeVisibleAnnotations extends Annotations {

	static public final String NAME = RuntimeVisibleAnnotations.class.getSimpleName();

	static public class Provider implements AttributeProvider {
		public String name() {
			return NAME;
		}
		public AttributeInfo createAttribute(ClassFileProvider owner) {
			return new RuntimeVisibleAnnotations(owner);
		}
	}

	public RuntimeVisibleAnnotations(ClassFileProvider owner) {
		super(owner);
	}
}
