package org.jackie.compiler_impl.jmodelimpl.attribute;

import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.attribute.AttributeSupport;
import org.jackie.jvm.JNode;
import org.jackie.utils.Assert;

/**
 * @author Patrik Beno
 */
public class GenericAttribute extends JAttributeImpl<AttributeInfo> {

	public GenericAttribute(JNode owner, String name, AttributeInfo value) {
		super(owner, name, value);
	}

	public void compile(AttributeSupport attributed) {
		Assert.logNotYetImplemented();
	}
}
