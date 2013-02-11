package org.jackie.compiler.extension;

import org.jackie.jvm.JNode;
import org.jackie.jvm.extension.Extensible;
import org.jackie.jvm.extension.Extension;

/**
 * @author Patrik Beno
 */
public interface ExtensionProvider<T extends JNode> extends Lifecycle {

	Class<? extends Extension> getType();

	Extension getExtension(T jnode);

}
