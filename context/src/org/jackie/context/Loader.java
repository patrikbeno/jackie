package org.jackie.context;

import org.jackie.utils.Log;
import org.jackie.utils.Assert;

import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;

/**
 * @author Patrik Beno
 */
public class Loader {

	protected String resource;

	protected Map<Class,Class> classByInterface;

	public Loader(String resource) {
		this.resource = resource;
		this.classByInterface = new HashMap<Class, Class>();
	}

	protected void loadResources() {
		try {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			Enumeration<URL> e = cl.getResources(resource);
			while (e.hasMoreElements()) {
				URL url = e.nextElement();
				Log.debug("Loading type:implementation mapping from %s", url);
				Properties props = new Properties();
				props.load(url.openStream());
				load(props);
			}

		} catch (IOException e) {
			throw Assert.notYetHandled(e);
		}
	}

	protected void load(Properties props) {
		Enumeration<?> names = props.propertyNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String implname = props.getProperty(name);
			try {
				Class iface = load(name);
				Class impl = load(implname);
				classByInterface.put(iface, impl);
			} catch (Exception e) {
				Assert.logNotYetImplemented();
			}
		}
	}

	protected Class load(String clsname) {
		try {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			Class<?> cls = Class.forName(clsname, false, cl);
			return cls;
		} catch (ClassNotFoundException e) {
			throw Assert.notYetHandled(e);
		}
	}

}