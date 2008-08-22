package org.jackie.jclassfile.model;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Patrik Beno
 */
public abstract class Base {

	abstract
	public void load(DataInput in) throws IOException;

	abstract
	public void save(DataOutput out) throws IOException;

}
