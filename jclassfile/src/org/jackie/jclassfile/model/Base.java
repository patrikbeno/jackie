package org.jackie.jclassfile.model;

import org.jackie.jclassfile.constantpool.ConstantPool;

import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutput;

/**
 * @author Patrik Beno
 */
public abstract class Base {

	abstract
	public void load(DataInput in) throws IOException;

	abstract
	public void save(DataOutput out) throws IOException;

}
