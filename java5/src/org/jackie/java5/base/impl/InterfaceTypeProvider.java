package org.jackie.java5.base.impl;

import org.jackie.compiler.event.ExtensionEvents;
import org.jackie.compiler.extension.ExtensionProvider;
import org.jackie.compiler.extension.Lifecycle;
import org.jackie.event.Events;
import org.jackie.java5.base.InterfaceType;
import org.jackie.jclassfile.flags.Access;
import org.jackie.jclassfile.flags.Flags;
import org.jackie.jclassfile.model.ClassFile;
import org.jackie.jvm.JClass;
import org.jackie.jvm.attribute.special.ExtensionAttribute;
import org.jackie.jvm.extension.Extension;

/**
 * @author Patrik Beno
 */
public class InterfaceTypeProvider implements ExtensionProvider<JClass>, Lifecycle {

	ExtensionEvents listener = new ExtensionEvents() {
		@Override
		public void resolveClassFlags(Flags flags, JClass jclass) {
			if (flags.isSet(Access.INTERFACE)) {
				jclass.extensions().edit().add(new InterfaceTypeImpl(jclass));
			}
		}
		@Override
		public void onCompile(JClass jclass, ClassFile classfile) {
			if (!jclass.extensions().supports(InterfaceType.class)) { return; }
			classfile.flags().set(Access.INTERFACE);
			classfile.flags().set(Access.ABSTRACT);
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
		return null; // should already be applied in ExtensionEvents.resolveClassFlags()
	}
}
