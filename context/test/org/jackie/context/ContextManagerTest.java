package org.jackie.context;

import org.testng.annotations.Test;
import static org.jackie.context.ContextManager.newContext;
import static org.jackie.context.ContextManager.context;
import static org.jackie.context.ContextManager.closeContext;
import org.jackie.utils.Log;

/**
 * @author Patrik Beno
 */
@Test
public class ContextManagerTest {

	public void test() {
		assert context() == null;

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
		assert context() == null;
	}

	public void unauthorizedClose() {
		try {
			newContext();
			close();
			assert false;
		} catch (AssertionError e) {
			Log.debug("%s", e);
		}
	}

	void close() {
		closeContext();
	}

}

class SampleContextObject implements ContextObject {
	String something = "koki";
}
