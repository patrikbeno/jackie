package org.jackie.java5.annotation.impl;

import org.jackie.compiler.extension.ExtensionProvider;
import org.jackie.compiler.extension.Lifecycle;
import org.jackie.compiler.event.ExtensionEvents;
import org.jackie.java5.annotation.AnnotationType;
import org.jackie.java5.base.impl.InterfaceTypeImpl;
import org.jackie.jvm.JClass;
import org.jackie.jvm.extension.Extension;
import org.jackie.utils.Assert;
import org.jackie.jclassfile.flags.Flags;
import org.jackie.jclassfile.flags.Access;
import org.jackie.jclassfile.model.ClassFile;
import org.jackie.event.Events;

/**
 * @author Patrik Beno
 */
public class AnnotationTypeProvider implements ExtensionProvider<JClass>, Lifecycle {

	ExtensionEvents listener = new ExtensionEvents() {
		public void resolveClassFlags(Flags flags, JClass jclass) {
			if (flags.isSet(Access.ANNOTATION)) {
				jclass.extensions().edit().add(new AnnotationTypeImpl(jclass));
			}
		}
		@Override
		public void onCompile(JClass jclass, ClassFile classfile) {
			if (!jclass.extensions().supports(AnnotationType.class)) { return; }
			classfile.flags().set(Access.ANNOTATION);
		}

	};

	public Class<? extends Extension> getType() {
		return AnnotationType.class;
	}

	public Extension getExtension(JClass jclass) {
		return null; // should already be applied in ExtensionEvents.resolveClassFlags()
	}

	public void init() {
		Events.registerEventListener(ExtensionEvents.class, listener);
	}

	public void done() {
		Events.unregisterEventListener(listener);
	}
}
