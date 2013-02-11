package org.jackie.java5.enumtype.impl;

import org.jackie.java5.AbstractExtension;
import org.jackie.java5.enumtype.EnumType;
import org.jackie.java5.enumtype.JEnumContant;
import org.jackie.jvm.JClass;
import org.jackie.jvm.extension.Extension;
import org.jackie.utils.Assert;

import java.util.List;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public class EnumTypeImpl extends AbstractExtension<JClass> implements EnumType {

	public EnumTypeImpl(JClass node) {
		super(node);
	}

	public Class<? extends Extension> type() {
		return EnumType.class;
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
