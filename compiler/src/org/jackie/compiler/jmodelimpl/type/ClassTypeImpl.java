package org.jackie.compiler.jmodelimpl.type;

import static org.jackie.compiler.util.Helper.assertEditable;
import org.jackie.jmodel.JClass;
import org.jackie.jmodel.extension.base.ClassNestingLevel;
import org.jackie.jmodel.extension.base.ClassType;
import org.jackie.jmodel.structure.JMethod;
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
