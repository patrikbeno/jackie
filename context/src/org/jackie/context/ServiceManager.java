package org.jackie.context;

import static org.jackie.context.ContextManager.context;
import org.jackie.utils.Assert;
import org.jackie.utils.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @author Patrik Beno
 */
public class ServiceManager extends Loader implements ContextObject {


	static public ServiceManager serviceManager() {
		ServiceManager instance = context(ServiceManager.class);
		if (instance == null) {
			instance = new ServiceManager();
			context().set(ServiceManager.class, instance);
		}
		return instance;
	}

	static public <T extends Service> T service(Class<T> type) {
		return serviceManager().getService(type);
	}


	public ServiceManager() {
		super("META-INF/org.jackie/services.properties");
	}

	protected Map<Class,Service> instanceByInterface;

	{
		instanceByInterface = new HashMap<Class, Service>();
		loadResources();
	}

	public <T extends Service> T getService(Class<T> type) {
		Service instance = instanceByInterface.get(type);
		if (instance != null) {
			return type.cast(instance);
		}

		Class cls = classByInterface.get(type);
		if (cls == null) {
			throw new NoSuchElementException(type.getName());
		}

		try {
			Log.debug("Creating service %s (implementation: %s)", type.getName(), cls.getName());
			instance = (Service) cls.newInstance();
			instanceByInterface.put(type, instance);

			return type.cast(instance);

		} catch (InstantiationException e) {
			throw Assert.notYetHandled(e);
		} catch (IllegalAccessException e) {
			throw Assert.notYetHandled(e);
		}

	}

}
