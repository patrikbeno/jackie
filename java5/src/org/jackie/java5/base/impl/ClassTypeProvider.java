package org.jackie.java5.base.impl;

import org.jackie.jvm.attribute.special.KindAttribute;
import org.jackie.jvm.attribute.special.Kind;
import org.jackie.jvm.attribute.JAttribute;
import org.jackie.compiler.extension.ExtensionProvider;
import org.jackie.jvm.JClass;
import org.jackie.jvm.extension.Extension;
import org.jackie.jvm.extension.builtin.JPrimitive;
import org.jackie.java5.base.ClassType;

import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

/**
 * @author Patrik Beno
 */
public class ClassTypeProvider implements ExtensionProvider<JClass> {

	static private final Set<String> IGNORED_CLASS_NAMES;

	static {
		IGNORED_CLASS_NAMES = Collections.unmodifiableSet(new HashSet<String>(){{
			for (JPrimitive p : JPrimitive.values()) {
				add(p.getPrimitiveClass().getName());
			}
			add("package-info");
		}});
	}

	public Class<? extends Extension> getType() {
		return ClassType.class;
	}

	public Extension<JClass> getExtension(JClass jclass) {

		if (IGNORED_CLASS_NAMES.contains(jclass.getName())) {
			return null;
		}

		JAttribute kind = jclass.attributes().getAttribute("Kind");
		assert kind != null;

		switch ((Kind) kind.getValue()) {
			case CLASS:
			case ENUM:
				return new ClassTypeImpl(jclass);
		}
		
		return null;
	}
}
