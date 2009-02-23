package org.jackie.compiler.event;

import org.jackie.event.Event;
import org.jackie.jvm.extension.Extensible;
import org.jackie.jvm.extension.Extension;
import org.jackie.jvm.attribute.Attributed;
import org.jackie.jvm.attribute.special.ExtensionAttribute;
import org.jackie.jvm.JClass;
import org.jackie.jclassfile.flags.Flags;
import org.jackie.jclassfile.model.ClassFile;

/**
 * @author Patrik Beno
 */
public abstract class ExtensionEvents implements Event {

	public void resolveClassFlags(Flags flags, JClass jclass) {}

	public void added(Extension extension) {}

	public void removed(Extension extension) {}

	public void onCompile(JClass jclass, ClassFile classfile) {}
}
