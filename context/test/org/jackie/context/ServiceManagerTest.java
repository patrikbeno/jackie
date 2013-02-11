package org.jackie.context;

import static org.jackie.context.ContextManager.*;
import static org.jackie.context.ServiceManager.service;
import org.junit.Test;

/**
 * @author Patrik Beno
 */
public class ServiceManagerTest {

    @Test
	public void loadService() {
		newContext();
		try {
			service(TestService.class).test();
		} finally {
			closeContext();
		}
	}

}
