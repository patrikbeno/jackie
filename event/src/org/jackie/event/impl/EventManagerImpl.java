package org.jackie.event.impl;

import org.jackie.event.Event;
import org.jackie.event.EventManager;
import org.jackie.utils.Assert;

import java.util.ArrayList;
import static java.util.Collections.emptyList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Patrik Beno
 */
public class EventManagerImpl implements EventManager {

	Map<Class, List<Event>> listenersByType;

	EventProxyProvider proxyProvider;

	{
		listenersByType = new HashMap<Class, List<Event>>();
		proxyProvider = new EventProxyProvider();
	}


	public <T extends Event> void registerEventListener(Class<T> type, T listener) {
		List<Event> listeners = listenersByType.get(type);
		if (listeners == null) {
			listeners = new ArrayList<Event>();
			listenersByType.put(type, listeners);
		}
		listeners.add(listener);
	}

	public <T extends Event> void unregisterEventListener(T listener) {
		throw Assert.notYetImplemented(); // todo implement this
	}

	public <T extends Event> List<T> getListeners(Class<T> type) {
		List<Event> listeners = listenersByType.get(type);
		return (listeners != null) ? new ArrayList(listeners) : emptyList();
	}

	public <T extends Event> T getEventDispatcher(Class<T> type) {
		return proxyProvider.getProxy(type);
	}

}
