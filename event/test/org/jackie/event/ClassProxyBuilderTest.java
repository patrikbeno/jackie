package org.jackie.event;

import static org.jackie.context.ContextManager.*;
import org.jackie.event.impl.proxygen.ClassProxyBuilder;
import static org.jackie.event.Events.eventManager;
import static org.jackie.utils.Assert.expected;
import org.jackie.utils.ObjectWrapper;
import org.testng.annotations.Test;
import org.objectweb.asm.util.TraceClassVisitor;
import org.objectweb.asm.ClassReader;

import java.io.PrintWriter;

/**
 * @author Patrik Beno
 */
public class ClassProxyBuilderTest {

	static public abstract class SampleEvents implements Event {

		public void fireEvent() {}

	}

	@Test
	public void generateProxy() throws Exception {
		ClassProxyBuilder b = new ClassProxyBuilder(SampleEvents.class);
		final byte[] bytes = b.build();

		ClassReader cr = new ClassReader(bytes);
		cr.accept(new TraceClassVisitor(new PrintWriter(System.out)), 0);
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
				public void fireEvent() {
					dispatched.set(true);
				}
			});

			SampleEvents sample = (SampleEvents) c.newInstance();
			sample.fireEvent();
		} finally {
			closeContext();
		}

		expected(true, dispatched.get(), "Event not dispatched!");

	}

}
