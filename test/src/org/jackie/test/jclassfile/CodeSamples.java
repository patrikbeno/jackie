package org.jackie.test.jclassfile;

import java.io.IOException;

/**
 * sample class for testing 'Code' attribute
 *
 * @author Patrik Beno
 */
public class CodeSamples {

	class NoCode {
	}

	class InvokeMethod {
		void test1() {
			System.out.println();
		}
		void test2() {
			new Object();
		}
		void test3() {
			new Object().toString();
		}
	}

	class Exceptions {
		void test() throws IOException, RuntimeException {
		}
	}

	class Switches {
		int i;
		void test() {
			System.out.println();
			switch (i) {
				case 1:
				case 3:
				case 5:
				case 100:
				case 1000:
				case 1000000:
				case 1000000000:
					System.out.println("case");
					break;
				default:
					System.out.println("default");

			}
		}
	}

}
