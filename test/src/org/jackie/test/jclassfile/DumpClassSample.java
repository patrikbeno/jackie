package org.jackie.test.jclassfile;

import org.jackie.utils.Assert;

import java.io.Serializable;
import java.io.Closeable;
import java.io.IOException;
import java.util.Date;

/**
 * @author Patrik Beno
 */
public class DumpClassSample implements Serializable, Closeable {

	String name;
	String surname;
	Date birthdate; 

	public void close() throws IOException {
		throw Assert.unsupported(); 
	}
}
