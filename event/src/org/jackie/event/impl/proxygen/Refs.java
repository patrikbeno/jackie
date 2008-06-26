package org.jackie.event.impl.proxygen;

import org.jackie.event.EventManager;
import org.jackie.utils.Assert;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.Iterator;

/**
 * @author Patrik Beno
 */
public class Refs {

	static Method EventManager$eventManager = method(EventManager.class, "eventManager");
	static Method EventManager$getListeners = method(EventManager.class, "getListeners", Class.class);

	static Method ClassProxyHelper$onException = method(ClassProxyHelper.class, "onException", Throwable.class);

	static Method Set$iterator = method(Set.class, "iterator");

	static Method Iterator$hasNext = method(Iterator.class, "hasNext");
	static Method Iterator$next = method(Iterator.class, "next");

	static Method method(Class cls, String name, Class... args) {
		try {
			return cls.getMethod(name, args);
		} catch (NoSuchMethodException e) {
			throw Assert.invariantFailed(e, "No such method: %s.%s", cls.getSimpleName(), name);
		}
	}

}
