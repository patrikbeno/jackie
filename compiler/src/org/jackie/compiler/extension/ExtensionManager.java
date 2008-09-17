package org.jackie.compiler.extension;

import org.jackie.context.ContextObject;
import org.jackie.jvm.JNode;
import org.jackie.jvm.extension.Extension;
import org.jackie.jvm.extension.Extensible;

import java.util.List;
import java.util.Set;


/**
 * @author Patrik Beno
 */
public interface ExtensionManager extends ContextObject {

	<T extends Extension> void registerExtension(Class<T> type, ExtensionProvider provider);

	<T extends Extension> void unregisterExtension(Class<T> type);

	<T extends Extension> Set<Class<T>> getExtensionTypes();

	List<ExtensionProvider> getProviders();

	ExtensionProvider getProvider(Class<? extends Extension> extensionType);

	<T extends Extension> T getExtension(Class<T> extensionType, Extensible extensible);
}
