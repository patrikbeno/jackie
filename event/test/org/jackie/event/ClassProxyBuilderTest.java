package org.jackie.event;

import static org.jackie.context.ContextManager.*;
import org.jackie.event.impl.proxygen.ClassProxyBuilder;
import static org.jackie.event.Events.eventManager;
import static org.jackie.utils.Assert.expected;
import static org.jackie.utils.Assert.invariantFailed;
import org.jackie.utils.ObjectWrapper;
import org.testng.annotations.Test;
import org.objectweb.asm.util.TraceClassVisitor;
import org.objectweb.asm.ClassReader;

import java.io.PrintWriter;

/**
 * @author Patrik Beno
 */
public class ClassProxyBuilderTest {

	public interface EventInterface extends Event {
		void interfaceEvent();
	}

	static public abstract class EventBase implements Event {
		public void inherited() {}
		public void inheritedOverriden() {}
	}

	static public abstract class SampleEvents extends EventBase implements EventInterface {
		public void valid() {}
		public void inheritedOverriden() {}
	}

	static public abstract class InvalidEvents implements Event {
		void invalid() {}
	}

	@Test
	public void generateProxy() throws Exception {
		ClassProxyBuilder b = new ClassProxyBuilder(SampleEvents.class);
		final byte[] bytes = b.build();

//		ClassReader cr = new ClassReader(bytes);
//		cr.accept(new TraceClassVisitor(new PrintWriter(System.out)), 0);
//		cr.accept(new ASMifierClassVisitor(new PrintWriter(System.out)), 0);

		Class c = Class.forName(SampleEvents.class.getName()+ ClassProxyBuilder.SUFFIX, true, new ClassLoader(getClass().getClassLoader()) {
			protected Class<?> findClass(String name) throws ClassNotFoundException {
				return defineClass(name, bytes, 0, bytes.length);
			}
		});

		final ObjectWrapper<Boolean> dispatched = new ObjectWrapper<Boolean>(false);

		newContext();
		try {
			eventManager().registerEventListener(SampleEvents.class, new SampleEvents() {
				public void valid() {
					dispatched.set(true);
				}
				public void interfaceEvent() {
					dispatched.set(true);
				}
			});

			SampleEvents sample = (SampleEvents) c.newInstance();

			sample.valid();
			expected(true, dispatched.get(), "Event not dispatched!");

			dispatched.set(false);
			sample.interfaceEvent();
			expected(true, dispatched.get(), "Event not dispatched!");

		} finally {
			closeContext();
		}

		expected(true, dispatched.get(), "Event not dispatched!");

	}

	@Test(expectedExceptions = {EventManagerException.class})
	public void validateEventClass() throws Exception {
		new ClassProxyBuilder(InvalidEvents.class).build();
	}

}
