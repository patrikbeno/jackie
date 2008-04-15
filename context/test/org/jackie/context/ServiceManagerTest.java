package org.jackie.context;

import static org.jackie.context.ContextManager.*;
import static org.jackie.context.ServiceManager.service;
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
