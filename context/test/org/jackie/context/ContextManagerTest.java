package org.jackie.context;

import static org.jackie.context.ContextManager.*;
import org.jackie.utils.Log;
import org.junit.Test;

/**
 * @author Patrik Beno
 */
public class ContextManagerTest {

    @Test
	public void test() {
		assert !contextManager().hasContext();

		newContext();
		try {
			assert context() != null;

			SampleContextObject co = new SampleContextObject();

			context().set(SampleContextObject.class, co);
			assert context(SampleContextObject.class) == co;

			context().remove(SampleContextObject.class);
			assert context(SampleContextObject.class) == null;

		} finally {
		   closeContext();
		}
		assert !contextManager().hasContext();
	}

    @Test
	public void withContext() {
		final SampleContextObject co = new SampleContextObject();
		executeWithContext(new ContextRunner() {
			public void init() {
				context().set(SampleContextObject.class, co);
			}
			public void execute() {
				assert context().get(SampleContextObject.class) != null : "No context object";
			}
		});
	}

    @Test
	public void unauthorizedClose() {
		newContext();
		try {
			close();
			assert false;
		} catch (AssertionError e) {
			Log.debug("%s", e);
		} finally {
			closeContext();
		}
	}

	void close() {
		closeContext();
	}

}

class SampleContextObject implements ContextObject {
	String something = "koki";
}
