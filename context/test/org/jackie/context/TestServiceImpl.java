package org.jackie.context;

import org.jackie.utils.Log;

/**
 * @author Patrik Beno
 */
public class TestServiceImpl implements TestService {
	public void test() {
		Log.debug("TestService.test()"); 
	}
}
