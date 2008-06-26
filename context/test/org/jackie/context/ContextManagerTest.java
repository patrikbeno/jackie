package org.jackie.context;

import static org.jackie.context.ContextManager.*;
import org.jackie.utils.Log;
import org.testng.annotations.Test;

/**
 * @author Patrik Beno
 */
@Test
public class ContextManagerTest {

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
