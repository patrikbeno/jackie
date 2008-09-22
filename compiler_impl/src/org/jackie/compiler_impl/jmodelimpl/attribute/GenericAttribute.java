package org.jackie.compiler_impl.jmodelimpl.attribute;

import org.jackie.jclassfile.attribute.AttributeSupport;
import org.jackie.jclassfile.model.AttributeInfo;
import org.jackie.jclassfile.model.ClassFile;
import org.jackie.jclassfile.model.ClassFileProvider;
import org.jackie.jvm.JNode;
import org.jackie.jvm.Editor;
import org.jackie.jvm.spi.JModelHelper;
import org.jackie.jvm.attribute.JAttribute;
import org.jackie.utils.Assert;

/**
 * @author Patrik Beno
 */
public class GenericAttribute implements JAttribute {

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

	public <T extends ClassFileProvider & AttributeSupport> void compile(T t) {
		// fixme attribute handling (this sucks)
		org.jackie.jclassfile.attribute.GenericAttribute newattr = new org.jackie.jclassfile.attribute.GenericAttribute(
				t,
				t.classFile().pool().factory().getUtf8(value.name()));
		newattr.data(((org.jackie.jclassfile.attribute.GenericAttribute) value).data());
		t.addAttribute(newattr);
	}

}
