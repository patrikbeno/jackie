package org.jackie.compiler.spi;

import org.jackie.context.Loader;
import static org.jackie.context.ContextManager.context;
import org.jackie.compiler.extension.ExtensionManager;
import org.jackie.compiler.extension.ExtensionProvider;
import org.jackie.compiler.extension.Lifecycle;
import static org.jackie.utils.Assert.NOTNULL;
import org.jackie.utils.Assert;
import org.jackie.jvm.extension.Extension;
import org.jackie.jvm.extension.builtin.PrimitiveType;

import java.util.Map;

/**
 * @author Patrik Beno
 */
public class DefaultExtensionManagerConfigurator extends Loader {

	static public void configure() {
		new DefaultExtensionManagerConfigurator().doConfigure();
	}

	public DefaultExtensionManagerConfigurator() {
		super("META-INF/org.jackie/extensions.properties");
	}

	void doConfigure() {
		ExtensionManager xm = NOTNULL(context(ExtensionManager.class));
		loadResources();
		for (Map.Entry<Class,Class> entry : classByInterface.entrySet()) {
			try {
				Class<Extension> type = entry.getKey();
				ExtensionProvider provider = (ExtensionProvider) entry.getValue().newInstance();
				provider.init();
				xm.registerExtension(type, provider);
			} catch (InstantiationException e) {
				Assert.logNotYetHandled(e);
			} catch (IllegalAccessException e) {
				Assert.logNotYetHandled(e);
			}
		}
	}


}
