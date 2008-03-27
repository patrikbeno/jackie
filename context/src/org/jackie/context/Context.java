package org.jackie.context;

import static org.jackie.utils.Assert.NOT;
import static org.jackie.utils.Assert.doAssert;

import java.util.Map;
import java.util.HashMap;

/**
 * @author Patrik Beno
 */
public class Context {

	static private final int STACKTRACE_OFFSET = 4;

	protected Context parent;
	protected StackTraceElement creator;
	protected Map<Class, Object> items;

	{
		items = new HashMap<Class, Object>();
	}

	public Context(Context parent) {
		this.parent = parent;
		this.creator = Thread.currentThread().getStackTrace()[STACKTRACE_OFFSET];
	}

	public <T extends ContextObject> T get(Class<T> type) {
		T t = type.cast(items.get(type));
		if (t == null && parent != null) {
			t = parent.get(type);
		}
		return t;
	}

	public <T extends ContextObject> void set(Class<T> type, T object) {
		items.put(type, org.jackie.utils.Assert.typecast(object, type));
	}

	public <T extends ContextObject> void remove(Class<T> type) {
		items.remove(type);
	}

	public void authorizeClose() {
		StackTraceElement ste = Thread.currentThread().getStackTrace()[STACKTRACE_OFFSET];
		doAssert(ste.getClassName().equals(creator.getClassName()) && ste.getMethodName().equals(creator.getMethodName()),
					"Unauthorized caller (%s). Context can be closed by its creator only (%s)", ste, creator);
	}

}
