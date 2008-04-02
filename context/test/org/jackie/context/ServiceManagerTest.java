package org.jackie.context;

import static org.jackie.context.ServiceManager.service;
import static org.jackie.context.ContextManager.newContext;
import static org.jackie.context.ContextManager.closeContext;
import org.testng.annotations.Test;

/**
 * @author Patrik Beno
 */
@Test
public class ServiceManagerTest {

	public void loadService() {
		newContext();
		try {
			service(TestService.class).test();
		} finally {
			closeContext();
		}
	}

}
