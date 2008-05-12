package org.jackie.event;

import org.jackie.utils.Assert;
import org.jackie.utils.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Patrik Beno
 */
public class EventDispatcherProxy implements InvocationHandler {

	EventManager manager;
	Class<? extends Event> type;
	Event proxy;

	public EventDispatcherProxy(EventManager manager, Class<? extends Event> type) {
		this.manager = manager;
		this.type = type;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (method.getDeclaringClass() != type) {
			Method m = getClass().getMethod(method.getName(), method.getParameterTypes());
			return m.invoke(this, args);
		}

		for (Event e : manager.getListeners(type)) {
			invoke(e, method, args);
		}

		return null;
	}

	protected void invoke(Event listener, Method method, Object[] args) {
		try {
			method.invoke(listener, args);
		} catch (IllegalAccessException e) {
			throw Assert.unexpected(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			Log.warn("Error dispatching event %s.%s. Failing listener: %s. Error: %s",
						type.getSimpleName(), method.getName(), listener, e.getCause());
		}
	}

	public Event getProxy() {
		if (proxy == null) {
			proxy = (Event) Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[] { type }, this);
		}
		return proxy;
	}
}
