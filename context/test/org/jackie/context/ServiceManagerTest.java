package org.jackie.context;

import static org.jackie.context.ServiceManager.service;
import org.testng.annotations.Test;

/**
 * @author Patrik Beno
 */
@Test
public class ServiceManagerTest {

	public void loadService() {
		service(TestService.class).test();
	}

}
