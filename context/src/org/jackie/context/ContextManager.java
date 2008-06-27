package org.jackie.context;

import static org.jackie.utils.Assert.NOTNULL;

/**
 * @author Patrik Beno
 */
public class ContextManager {

	static private final ContextManager INSTANCE = new ContextManager();

	static public ContextManager contextManager() {
		return INSTANCE;
	}

	static public Context newContext() {
		return contextManager().beginNewContext();
	}

	static public Context closeContext() {
		return contextManager().closeCurrentContext();
	}

	static public Context context() {
		return contextManager().getCurrentContext();
	}

	static public <T extends ContextObject> T context(Class<T> type) {
		return contextManager().getCurrentContext().get(type);
	}

	public boolean hasContext() {
		return tlContext.get() != null;
	}

	///

	ThreadLocal<Context> tlContext;

	{
		tlContext = new ThreadLocal<Context>();
	}

	///

	protected Context beginNewContext() {
		Context ctx = new Context(tlContext.get());
		tlContext.set(ctx);
		return ctx;
	}

	protected Context closeCurrentContext() {
		Context ctx = getCurrentContext();

		ctx.authorizeClose();

		ctx = ctx.parent;
		tlContext.set(ctx);

		return ctx;
	}

	public Context getCurrentContext() {
		return NOTNULL(tlContext.get(), "No Context!");
	}


}
