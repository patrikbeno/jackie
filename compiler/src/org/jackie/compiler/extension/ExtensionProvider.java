package org.jackie.compiler.extension;

import org.jackie.jvm.JNode;
import org.jackie.jvm.extension.Extensible;
import org.jackie.jvm.extension.Extension;

/**
 * @author Patrik Beno
 */
public interface ExtensionProvider<T extends JNode & Extensible> {

	Class<? extends Extension> getType();

	Extension<T> getExtension(T node);

}
