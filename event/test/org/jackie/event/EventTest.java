package org.jackie.event;

import org.testng.annotations.Test;
import static org.jackie.context.ContextManager.context;
import static org.jackie.context.ContextManager.newContext;
import static org.jackie.context.ContextManager.closeContext;
import org.jackie.context.ContextObject;
import static org.jackie.event.Events.events;
import org.jackie.utils.Log;

/**
 * @author Patrik Beno
 */
@Test
public class EventTest {

	static public interface ITestEvents extends Event {
		void testEvent();
	}

	static public class CTestEvents implements Event {
		public void testEvent() {}
	}

	static class Ctx implements ContextObject {
		int listenerInvocations;
	}

	@Test
	public void sendEvent() {
		newContext();
		try {
			events(ITestEvents.class).testEvent();
		} finally {
			closeContext();
		}
	}

	@Test
	public void interfaceEvents() {
		newContext();
		try {
			context().set(Ctx.class, new Ctx());

			Events.registerEventListener(ITestEvents.class, listener());
			Events.registerEventListener(ITestEvents.class, listener());

			events(ITestEvents.class).testEvent();

			assert context(Ctx.class).listenerInvocations == 2;

		} finally {
			closeContext();
		}
	}

	@Test
	public void classEvents() {
		newContext();
		try {
			context().set(Ctx.class, new Ctx());

			Events.registerEventListener(CTestEvents.class, clistener());
			Events.registerEventListener(CTestEvents.class, clistener());

			events(CTestEvents.class).testEvent();

			assert context(Ctx.class).listenerInvocations == 2;

		} finally {
			closeContext();
		}
	}

	ITestEvents listener() {
		return new ITestEvents() {
			public void testEvent() {
				context(Ctx.class).listenerInvocations++;
				Log.debug("ITestEvents.testEvent(): in listener !");
			}
		};
	}

	CTestEvents clistener() {
		return new CTestEvents() {
			public void testEvent() {
				context(Ctx.class).listenerInvocations++;
				Log.debug("CTestEvents.testEvent(): in listener !");
			}
		};
	}

}
