package org.jackie.event.impl.proxygen;

import org.jackie.event.Event;
import static org.jackie.event.Events.eventManager;
import org.jackie.utils.Assert;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Patrik Beno
 */
public class ClassProxyHelper {
	
	static Method ClassProxyHelper$eventListeners = method(ClassProxyHelper.class, "eventListeners", Class.class);
	static Method ClassProxyHelper$onException = method(ClassProxyHelper.class, "onException", Throwable.class);
	static Method Set$iterator = method(Set.class, "iterator");
	static Method Iterator$hasNext = method(Iterator.class, "hasNext");
	static Method Iterator$next = method(Iterator.class, "next");

	static public <T extends Event> Iterator<T> eventListeners(Class<T> type) {
		assert eventManager() != null : "No EventManager!";
		return eventManager().getListeners(type).iterator();
	}

	static public void onException(Throwable thrown) {
		System.err.println("onException()");
		thrown.printStackTrace();
	}


	static Method method(Class cls, String name, Class... args) {
		try {
			return cls.getDeclaredMethod(name, args);
		} catch (NoSuchMethodException e) {
			throw Assert.invariantFailed(e, "No such method: %s.%s", cls.getSimpleName(), name);
		}
	}
}
