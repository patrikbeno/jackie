package org.jackie.jclassfile.util;

import org.jackie.utils.Assert;
import org.jackie.utils.Log;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Patrik Beno
 */
public class AttributeProviderRegistry {

	static public final String RESOURCE = "META-INF/org.jackie.jclassfile.util.AttributeProvider.properties";

	static private final AttributeProviderRegistry INSTANCE = new AttributeProviderRegistry();

	static public AttributeProviderRegistry instance() {
		return INSTANCE;
	}

	Map<String, AttributeProvider> providers;

	{
		init();
	}

	public void addProvider(String name, AttributeProvider provider) {
		providers.put(name, provider);
	}

	public AttributeProvider getProvider(String name) {
		return providers.get(name);
	}

	void init() {
		try {
			providers = new HashMap<String, AttributeProvider>();
			Enumeration<URL> e = Thread.currentThread().getContextClassLoader().getResources(RESOURCE);
			while (e.hasMoreElements()) {
				Properties props = new Properties();
				props.load(e.nextElement().openStream());
				populate(props);
			}
		} catch (IOException e1) {
			throw Assert.notYetHandled(e1);
		}
	}

	private void populate(Properties props) {
		Enumeration<?> names = props.propertyNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String clsname = props.getProperty(name);
			createProvider(name, clsname);
		}
	}

	private void createProvider(String name, String clsname) {
		try {
			Log.debug("Creating AttributeProvider %s -> %s", name, clsname);
			Class<?> cls = Class.forName(clsname, false, Thread.currentThread().getContextClassLoader());
			AttributeProvider provider = (AttributeProvider) cls.newInstance();
			providers.put(name, provider);

		} catch (ClassNotFoundException e) {
			throw Assert.notYetHandled(e);
		} catch (IllegalAccessException e) {
			throw Assert.notYetHandled(e);
		} catch (InstantiationException e) {
			throw Assert.notYetHandled(e);
		}
	}

}
