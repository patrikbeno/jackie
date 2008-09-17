package org.jackie.java5.base.impl;

import org.jackie.compiler.extension.ExtensionProvider;
import org.jackie.java5.base.ClassType;
import org.jackie.jvm.JClass;
import org.jackie.jvm.extension.Extension;
import org.jackie.jvm.extension.builtin.JPrimitive;
import org.jackie.utils.Assert;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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

	public Extension getExtension(JClass jclass) {

		if (IGNORED_CLASS_NAMES.contains(jclass.getName())) {
			return null;
		}

		
		return null;
	}

	public void init() {
		throw Assert.notYetImplemented(); // todo implement this 
	}

	public void done() {
		throw Assert.notYetImplemented(); // todo implement this
	}
}
