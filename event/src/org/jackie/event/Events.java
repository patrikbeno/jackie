package org.jackie.event;

import static org.jackie.context.ServiceManager.service;

/**
 * @author Patrik Beno
 */
public class Events {

	static public <T extends Event> T events(Class<T> type) {
		return service(EventManager.class).getEventDispatcher(type);
	}

	static public <T extends Event> void registerEventListener(Class<T> type, T listener) {
		service(EventManager.class).registerEventListener(type, listener);
	}
	              
	static public <T extends Event> void unregisterEventListener(T listener) {
		service(EventManager.class).unregisterEventListener(listener);
	}

}
