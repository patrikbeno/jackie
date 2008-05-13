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

	static public interface TestEvents extends Event {
		void simpleEvent();
	}

	static class Ctx implements ContextObject {
		int listenerInvocations;
	}

	public void testSendEvent() {
		newContext();
		try {
			events(TestEvents.class).simpleEvent();
		} finally {
			closeContext();
		}
	}

	public void testEventListener() {
		newContext();
		try {
			context().set(Ctx.class, new Ctx());

			Events.registerEventListener(TestEvents.class, listener());
			Events.registerEventListener(TestEvents.class, listener());

			events(TestEvents.class).simpleEvent();

			assert context(Ctx.class).listenerInvocations == 2;

		} finally {
			closeContext();
		}
	}

	TestEvents listener() {
		return new TestEvents() {
			public void simpleEvent() {
				context(Ctx.class).listenerInvocations++;
				Log.debug("TestEvents.simpleEvent(): in listener !");
			}
		};
	}

}
