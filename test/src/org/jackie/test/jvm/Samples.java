package org.jackie.test.jvm;


/**
 * @author Patrik Beno
 */
public class Samples {

	static public class Clazz {}

	static interface Interface {
		int CONSTANT = 1;
	}

	static class Fields {
		int fdefault;
		static int fstatic;
		public int fpublic;
		protected int fprotected;
	}

	static class Methods {
		void debugging(char[] c) {}
	}

	static class Bug {
		final String CONSTANT = "hey!";
		String test() { return CONSTANT; }
	}

    static @interface Version {
        String value();
    }

	@Version("1.0")
	static class Annotated {}
}
