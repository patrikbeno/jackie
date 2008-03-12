package org.jackie.compiler.jmodelimpl.type;

import org.jackie.jmodel.JClass;
import org.jackie.jmodel.extension.enumtype.EnumType;
import org.jackie.jmodel.extension.enumtype.JEnumContant;
import org.jackie.utils.Assert;

import java.util.Set;
import java.util.List;

/**
 * @author Patrik Beno
 */
public class EnumTypeImpl extends AbstractExtension<JClass> implements EnumType {

	public EnumTypeImpl(JClass node) {
		super(node);
	}

	public Set<String> getEnumConstantNames() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public JEnumContant getEnumConstant(String name) {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public JEnumContant getEnumConstant(int ordinal) {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public List<JEnumContant> getEnumContants() {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public boolean isEditable() {
		return node().isEditable();
	}

	public Editor edit() {
		return new Editor() {
			public EnumType editable() {
				return EnumTypeImpl.this;
			}
		};
	}
}
