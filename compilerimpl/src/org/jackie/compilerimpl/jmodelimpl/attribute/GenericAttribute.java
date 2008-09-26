package org.jackie.compilerimpl.jmodelimpl.attribute;

import org.jackie.jclassfile.attribute.AttributeSupport;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jvm.JNode;
import org.jackie.jvm.spi.JModelHelper;
import org.jackie.jvm.attribute.JAttribute;
import org.jackie.compiler.spi.NodeCompiler;

/**
 * @author Patrik Beno
 */
public class GenericAttribute implements JAttribute, NodeCompiler<AttributeSupport> {

	JNode owner;
	String name;
	AttributeInfo value;

	public GenericAttribute(JNode owner, String name, AttributeInfo value) {
		this.owner = owner;
		this.name = name;
		this.value = value;
	}

	public JNode owner() {
		return owner;
	}

	public String getName() {
		return name;
	}

	public AttributeInfo getValue() {
		return value;
	}

	public boolean isEditable() {
		return JModelHelper.isEditable(owner());
	}

	public interface Editor extends org.jackie.jvm.Editor<GenericAttribute> {
		Editor setValue(AttributeInfo value);
	}

	public Editor edit() {
		return new Editor() {
			public Editor setValue(AttributeInfo value) {
				GenericAttribute.this.value = value;
				return this;
			}

			public GenericAttribute editable() {
				return GenericAttribute.this;
			}
		};
	}

	public void compile(AttributeSupport context) {
		context.addAttribute(value);
	}
	
}
