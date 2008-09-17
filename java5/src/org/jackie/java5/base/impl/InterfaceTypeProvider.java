package org.jackie.java5.base.impl;

import org.jackie.compiler.extension.ExtensionProvider;
import org.jackie.compiler.extension.Lifecycle;
import org.jackie.compiler.event.ExtensionEvents;
import org.jackie.java5.base.InterfaceType;
import org.jackie.jvm.JClass;
import org.jackie.jvm.attribute.special.ExtensionAttribute;
import org.jackie.jvm.extension.Extension;
import org.jackie.jclassfile.flags.Flags;
import org.jackie.jclassfile.flags.Access;
import org.jackie.event.Events;

/**
 * @author Patrik Beno
 */
public class InterfaceTypeProvider implements ExtensionProvider<JClass>, Lifecycle {

	ExtensionEvents listener = new ExtensionEvents() {
		public void unresolvedExtensionAttribute(Flags flags, ExtensionAttribute xattr) {
			if (flags.isSet(Access.INTERFACE) || flags.isSet(Access.ANNOTATION)) {
				xattr.edit().addExtension(InterfaceType.class);
			}
		}
	};

	public void init() {
		Events.registerEventListener(ExtensionEvents.class, listener);
	}

	public void done() {
		Events.unregisterEventListener(listener);
	}


	public Class<? extends Extension> getType() {
		return InterfaceType.class;
	}

	public Extension getExtension(JClass jclass) {
		ExtensionAttribute xattr = (ExtensionAttribute) jclass.attributes().getAttribute(ExtensionAttribute.NAME);
		if (xattr.contains(InterfaceType.class)) {
			return new InterfaceTypeImpl(jclass);
		} else {
			return null;
		}
	}
}