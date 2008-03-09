package org.jackie.jmodel.extension;

import org.jackie.jmodel.JNode;

/**
 * @author Patrik Beno
 */
public interface ExtensionProvider<T extends JNode & Extensible> {

	Class<? extends Extension> getType();

	Extension getExtension(T node);

}
