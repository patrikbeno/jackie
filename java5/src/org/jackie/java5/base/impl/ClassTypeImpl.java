package org.jackie.java5.base.impl;

import org.jackie.java5.AbstractExtension;
import org.jackie.java5.annotation.Annotations;
import org.jackie.java5.base.ClassNestingLevel;
import org.jackie.java5.base.ClassType;
import org.jackie.jvm.JClass;
import org.jackie.jvm.structure.JMethod;
import org.jackie.utils.Assert;

import java.util.List;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public class ClassTypeImpl extends AbstractExtension<JClass> implements ClassType {

	public ClassTypeImpl(JClass node) {
		super(node);
	}

	public ClassNestingLevel getNestingKind() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public JClass getEnclosingClass() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public JMethod getEnclosingMethod() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public Set<JClass> getNestedClasses() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public List<JMethod> getConstructors() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public List<JMethod> getMethods() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public Annotations annotations() {
		return node().extensions().get(Annotations.class);
	}

	public boolean isEditable() {
		return node().isEditable();
	}

	public Editor edit() {
		return new Editor() {
			public void addConstructor(JMethod ctor) {
				throw Assert.notYetImplemented(); // todo implement this
			}

			public ClassType editable() {
				return ClassTypeImpl.this;
			}
		};
	}
}
