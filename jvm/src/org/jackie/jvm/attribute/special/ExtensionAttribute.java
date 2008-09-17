package org.jackie.jvm.attribute.special;

import org.jackie.jvm.JNode;
import org.jackie.jvm.attribute.JAttribute;
import org.jackie.jvm.extension.Extension;
import org.jackie.jvm.spi.JModelHelper;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public class ExtensionAttribute implements JAttribute {

	static public final String NAME = ExtensionAttribute.class.getSimpleName();

	protected JNode node;
	protected Set<Class<? extends Extension>> extensions;

	public ExtensionAttribute(JNode node) {
		this.node = node;
	}

	public String getName() {
		return NAME;
	}

	public JNode owner() {
		return node;
	}

	public boolean isEditable() {
		return JModelHelper.isEditable(owner());
	}

	public Object getValue() {
		return getExtensionTypes();
	}

	public Set<Class<? extends Extension>> getExtensionTypes() {
		if (extensions == null) { return Collections.emptySet(); }

		return Collections.unmodifiableSet(extensions);
	}

	public boolean contains(Class<? extends Extension> type) {
		return getExtensionTypes().contains(type);
	}

	public interface Editor extends org.jackie.jvm.Editor<ExtensionAttribute> {
		Editor addExtension(Class<? extends Extension> extension);
	}

	public Editor edit() {
		return new Editor() {
			public ExtensionAttribute editable() {
				return ExtensionAttribute.this;
			}

			public Editor addExtension(Class<? extends Extension> extension) {
				if (extensions == null) {
					extensions = new HashSet<Class<? extends Extension>>();
				}
				extensions.add(extension);
				return this;
			}
		};
	}

}
