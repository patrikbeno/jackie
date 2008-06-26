package org.jackie.event;

import org.jackie.context.Service;
import static org.jackie.context.ServiceManager.service;
import org.jackie.utils.Assert;
import org.jackie.event.impl.EventDispatcherProxy;
import org.jackie.event.impl.EventProxyProvider;
import org.jackie.event.impl.proxygen.ClassProxyBuilder;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import static java.util.Collections.emptyList;

/**
 * @author Patrik Beno
 */
public interface EventManager extends Service {

	<T extends Event> void registerEventListener(Class<T> type, T listener);

	<T extends Event> void unregisterEventListener(T listener);

	<T extends Event> List<T> getListeners(Class<T> type);

	<T extends Event> T getEventDispatcher(Class<T> type);
}
