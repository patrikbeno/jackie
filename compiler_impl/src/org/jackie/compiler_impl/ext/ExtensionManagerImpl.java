package org.jackie.compiler_impl.ext;

import org.jackie.compiler.extension.ExtensionManager;
import org.jackie.compiler.extension.ExtensionProvider;
import org.jackie.jvm.JNode;
import org.jackie.jvm.extension.Extension;
import org.jackie.jvm.extension.Extensible;
import org.jackie.utils.Assert;
import static org.jackie.utils.Assert.typecast;
import static org.jackie.utils.Assert.NOTNULL;
import static org.jackie.utils.Assert.expected;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.Collections;
import java.util.ArrayList;

/**
 * @author Patrik Beno
 */
public class ExtensionManagerImpl implements ExtensionManager {

	static ThreadLocal<List<Class<? extends Extension>>> tlInProgress;

	static {
		tlInProgress = new ThreadLocal<List<Class<? extends Extension>>>();
	}

	//META-INF/org.jackie/extensions.properties

	Map<Class<? extends Extension>, ExtensionProvider> providers;

	{
		providers = new HashMap<Class<? extends Extension>, ExtensionProvider>();
	}

	public <T extends Extension> void registerExtension(Class<T> type, ExtensionProvider provider) {
		NOTNULL(type);
		NOTNULL(provider);
		expected(type, provider.getType(), "Invalid provider type");

		providers.put(type, provider);
	}

	public <T extends Extension> void unregisterExtension(Class<T> type) {
		NOTNULL(type);
		providers.remove(type);
	}

	public <T extends Extension> Set<Class<T>> getExtensionTypes() {
		//noinspection unchecked
		return Collections.unmodifiableSet((Set)providers.keySet());
	}

	public List<ExtensionProvider> getProviders() {
		return Collections.unmodifiableList(new ArrayList<ExtensionProvider>(providers.values()));
	}

	public ExtensionProvider getProvider(Class<? extends Extension> extensionType) {
		return providers.get(extensionType);
	}

	public <T extends Extension> T getExtension(Class<T> extensionType, Extensible extensible) {
		ExtensionProvider provider = getProvider(extensionType);
		Extension extension = provider.getExtension((JNode) extensible);
		return (T) extension;
	}

}
