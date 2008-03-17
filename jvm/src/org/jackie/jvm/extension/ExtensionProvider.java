package org.jackie.jvm.extension;

import org.jackie.jvm.JNode;

/**
 * @author Patrik Beno
 */
public interface ExtensionProvider<T extends JNode & Extensible> {

	Class<? extends Extension> getType();

	Extension getExtension(T node);

}
