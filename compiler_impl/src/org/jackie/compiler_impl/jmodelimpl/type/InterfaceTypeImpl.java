package org.jackie.compiler_impl.jmodelimpl.type;

import org.jackie.jvm.JClass;
import org.jackie.java5.base.InterfaceType;
import org.jackie.jvm.structure.JField;
import org.jackie.jvm.structure.JMethod;
import org.jackie.utils.Assert;

import java.util.List;

/**
 * @author Patrik Beno
 */
public class InterfaceTypeImpl extends AbstractExtension<JClass> implements InterfaceType {

	public InterfaceTypeImpl(JClass node) {
		super(node);
	}

	public List<JField> getConstants() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public List<JMethod> getInterfaceMethods() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public boolean isEditable() {
		return node().isEditable();
	}

	public Editor edit() {
		throw Assert.notYetImplemented(); // todo implement this
	}
}
