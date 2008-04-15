package org.jackie.compiler.extension;

import org.jackie.context.ContextObject;
import org.jackie.jvm.JNode;
import org.jackie.jvm.extension.Extension;


/**
 * @author Patrik Beno
 */
public interface ExtensionManager extends ContextObject {

	<T extends Extension> T apply(JNode jnode, Class<T> extensionType );
}
